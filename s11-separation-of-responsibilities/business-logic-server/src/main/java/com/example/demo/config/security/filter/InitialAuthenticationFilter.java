package com.example.demo.config.security.filter;

import com.example.demo.config.security.auth.OtpAuthentication;
import com.example.demo.config.security.auth.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.signing.key:ba33eb65-c792-45f3-82f8-8422fe3c33d4}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        String username = httpServletRequest.getHeader("username");
        String password = httpServletRequest.getHeader("password");
        String otp = httpServletRequest.getHeader("otp");

        if (otp == null) {
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, otp);
            authentication = authenticationManager.authenticate(authentication);

            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(secretKey)
                    .compact();

            httpServletResponse.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }

}
