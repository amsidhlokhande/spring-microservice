package com.amsidh.mvc.usermicroservice.config;

import com.amsidh.mvc.usermicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        log.info(String.format("Gateway IP Address %s is allowed to access users-ws", environment.getProperty("gateway.ip.address")));
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress(environment.getProperty("gateway.ip.address"))
                .and().addFilter(getAuthenticationFilter());
        //Allow H2 Console web access
        http.headers().frameOptions().disable();
    }

    private AuthFilter getAuthenticationFilter() throws Exception {
        AuthFilter authFilter = new AuthFilter(userService,environment);
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }

   /* @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return emailId -> userService.loadUserByUsername(emailId);
    }
*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
