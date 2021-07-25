package com.amsidh.mvc.usermicroservice.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public CustomRetry getCustomRetry() {
        return new CustomRetry();
    }
}

@Slf4j
class CustomRetry implements Retryer {

    private final int maxAttempts;
    private final long backoff;
    int attempt;

    public CustomRetry() {
        this(2000, 3);
    }

    public CustomRetry(long backoff, int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());
        if (attempt++ >= maxAttempts) {
            throw e;
        }

        try {
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new CustomRetry(backoff, maxAttempts);
    }
}
