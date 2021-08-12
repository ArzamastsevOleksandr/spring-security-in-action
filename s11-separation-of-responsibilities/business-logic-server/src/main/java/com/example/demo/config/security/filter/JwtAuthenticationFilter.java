package com.example.demo.config.security.filter;

import com.example.demo.config.security.auth.UsernamePasswordAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key:ba33eb65-c792-45f3-82f8-8422fe3c33d4}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = httpServletRequest.getHeader("Authorization");

        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String username = String.valueOf(claims.get("username"));
        GrantedAuthority authority = new SimpleGrantedAuthority("user");
        Authentication authentication = new UsernamePasswordAuthentication(username, null, List.of(authority));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login");
    }

}
