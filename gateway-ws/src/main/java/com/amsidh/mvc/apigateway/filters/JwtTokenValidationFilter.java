package com.amsidh.mvc.apigateway.filters;

import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class JwtTokenValidationFilter extends AbstractGatewayFilterFactory<JwtTokenValidationFilter.Config> {

    private final Environment environment;

    public JwtTokenValidationFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest httpRequest = exchange.getRequest();
            if (!httpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Request does not contain Authorization header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = httpRequest.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwtToken = authorizationHeader.replace("Bearer", "");
            if(!validateJwtToken(jwtToken)){
                return onError(exchange, "Jwt token is invalid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private boolean validateJwtToken(String jwtToken) {
        String userId = Jwts.parser()
                .setSigningKey(environment.getProperty("jwt.secret.salt"))
                .parseClaimsJws(jwtToken)
                .getBody().getSubject();
        if(Optional.ofNullable(userId).isPresent()){
            return true;
        }
        return false;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
    }

}
