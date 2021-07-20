package com.amsidh.mvc.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


//@Configuration
public class AppConfig {

    //@Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("users-ws-status-check", predicateSpec -> predicateSpec.path("/users-ws/users/status/check")
                        .and().method("GET")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.removeRequestHeader("Cookie"))
                        .uri("lb://users-ws"))
                /*.route("account-ws-status-check", predicateSpec -> predicateSpec.path("/account-ws/account/status/check")
                        .and().method("GET")
                        .and().host("localhost*")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.removeRequestHeader("Cookie"))
                        .uri("lb://account-ws"))*/
                .build();

    }
}
