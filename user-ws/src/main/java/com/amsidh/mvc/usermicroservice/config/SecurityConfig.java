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
        //Disable CSRF attack
        http.csrf().disable();
        //Allow H2 Console web access
        http.headers().frameOptions().disable();


        log.info(String.format("Gateway IP Address %s is allowed to access users-ws", environment.getProperty("gateway.ip.address", "127.0.0.1/32")));
        http.authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress(environment.getProperty("gateway.ip.address", "127.0.0.1/32"))
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter());

    }

    private AuthFilter getAuthenticationFilter() throws Exception {
        AuthFilter authFilter = new AuthFilter(userService,environment);
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path", "/users/login"));
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
