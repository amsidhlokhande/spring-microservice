package com.amsidh.mvc.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalFilterConfig {

    @Order(1)
    @Bean
    public GlobalFilter firstGlobalFilter() {
        return ((exchange, chain) -> {
            log.info("First global Pre Filter called");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("First global Post Filter called");
            }));
        });
    }

    @Order(2)
    @Bean
    public GlobalFilter secondGlobalFilter() {
        return ((exchange, chain) -> {
            log.info("Second global Pre Filter called");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Second global Post Filter called");
            }));
        });
    }

    @Order(3)
    @Bean
    public GlobalFilter thirdGlobalFilter() {
        return ((exchange, chain) -> {
            log.info("Third global Pre Filter called");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Third global Post Filter called");
            }));
        });
    }

    @Order(4)
    @Bean
    public GlobalFilter fourthGlobalFilter() {
        return ((exchange, chain) -> {
            log.info("Fourth global Pre Filter called");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Fourth global Post Filter called");
            }));
        });
    }
}
