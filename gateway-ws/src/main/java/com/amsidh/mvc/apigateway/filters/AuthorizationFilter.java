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

import java.util.Objects;
import java.util.Optional;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final Environment environment;

    public AuthorizationFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest httpRequest = exchange.getRequest();
            if (!httpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Request does not contain Authorization header");
            }
            String authorizationHeader = Objects.requireNonNull(httpRequest.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String jwtToken = authorizationHeader.replace("Bearer", "");
            if(!validateJwtToken(jwtToken)){
                return onError(exchange, "Jwt token is invalid");
            }

            return chain.filter(exchange);
        };
    }

    private boolean validateJwtToken(String jwtToken) {
        String userId;
        try{
            userId= Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret.salt"))
                    .parseClaimsJws(jwtToken)
                    .getBody().getSubject();
        }catch(Exception ex){
            return false;
        }
        return Optional.ofNullable(userId).isPresent();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static class Config {
    }

}
