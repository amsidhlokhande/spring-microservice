package com.amsidh.mvc.usermicroservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        log.info(String.format("Gateway IP Address %s is allowed to access users-ws", environment.getProperty("gateway.ip.address")));
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress(environment.getProperty("gateway.ip.address"))
                .anyRequest().permitAll();
        //Allow H2 Console web access
        http.headers().frameOptions().disable();
    }
}
