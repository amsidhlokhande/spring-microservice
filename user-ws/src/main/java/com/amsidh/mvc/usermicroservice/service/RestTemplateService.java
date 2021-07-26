package com.amsidh.mvc.usermicroservice.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface RestTemplateService {

    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L))
    ResponseEntity<?> getResponseEntity(String url, HttpMethod httpMethod, HttpEntity<?> httpEntity, ParameterizedTypeReference<?> parameterizedTypeReference);

    @Recover
    ResponseEntity<?> getResponseEntityFallback(RuntimeException exception);
}
