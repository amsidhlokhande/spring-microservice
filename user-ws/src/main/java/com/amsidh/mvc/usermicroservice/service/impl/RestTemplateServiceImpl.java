package com.amsidh.mvc.usermicroservice.service.impl;

import com.amsidh.mvc.usermicroservice.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private final transient RestTemplate restTemplate;
    private transient int attempt = 0;

    @Override
    public ResponseEntity<?> getResponseEntity(String url, HttpMethod httpMethod, HttpEntity<?> httpEntity, ParameterizedTypeReference<?> parameterizedTypeReference) {
        log.info("Calling  {} attempt {}", url, attempt++);
        ResponseEntity<?> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, parameterizedTypeReference);
        log.info("Response: {}" + responseEntity.getBody());
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> getResponseEntityFallback(RuntimeException exception) {
        attempt = 0;
        log.error(String.format("Service call failed permanently with error %s", exception.getLocalizedMessage()));
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
