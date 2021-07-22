package com.amsidh.mvc.usermicroservice.config;

import com.amsidh.mvc.usermicroservice.service.UserService;
import com.amsidh.mvc.usermicroservice.ui.request.UserLoginModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginModel userLoginModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginModel.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userLoginModel.getEmailId(), userLoginModel.getPassword(), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String emailId = ((User) authResult.getPrincipal()).getUsername();
        String userId = userService.getUserIdByEmailId(emailId);

        String jwtToken = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration.time.in.millisecond")))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret.salt"))
                .compact();
        response.addHeader("token", jwtToken);
        response.addHeader("userId",userId);
    }
}
