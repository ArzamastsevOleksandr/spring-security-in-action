package com.example.demo.config.security;

import com.example.demo.config.security.filter.InitialAuthenticationFilter;
import com.example.demo.config.security.filter.JwtAuthenticationFilter;
import com.example.demo.config.security.provider.OtpAuthenticationProvider;
import com.example.demo.config.security.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final InitialAuthenticationFilter initialAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();

        http
                .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        http
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
