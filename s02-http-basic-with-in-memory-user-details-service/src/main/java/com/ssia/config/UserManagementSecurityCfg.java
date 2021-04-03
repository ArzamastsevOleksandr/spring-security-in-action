package com.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Configuration
public class UserManagementSecurityCfg {

    public static final Map<String, UserDetails> USERS = Map.of(
            "u", new CustomUserDetails("u", "p", "read"),
            "uu", new CustomUserDetails("uu", "pp", "read")
    );

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsService(USERS.values());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
