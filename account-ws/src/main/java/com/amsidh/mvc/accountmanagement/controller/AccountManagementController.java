package com.amsidh.mvc.accountmanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountManagementController {
    private final Environment environment;

    @GetMapping("/status/check")
    public String statusCheck(){
        return "Account Management MicroService Working on " + environment.getProperty("local.server.port");
    }
}
