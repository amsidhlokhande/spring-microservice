package com.amsidh.mvc.usermicroservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryAutoConfiguration;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@Import(SpringRetryAutoConfiguration.class)
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @LoadBalanced
    @Bean
    public RestTemplate retryableRestTemplate() {
        return new RestTemplate();
    }

   @Bean
    public Customizer<SpringRetryCircuitBreakerFactory> defaultCustomizer() {
       TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
       timeoutRetryPolicy.setTimeout(5000L);
       BackOffPolicy backOffPolicy = new FixedBackOffPolicy();


       return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
               .forceRefreshState(true)
               .backOffPolicy(backOffPolicy)
                .retryPolicy(timeoutRetryPolicy).build());
    }
}
