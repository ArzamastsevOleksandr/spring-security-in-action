package com.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Map;

@Configuration
public class UserManagementSecurityCfg {

    public static final Map<String, UserDetails> USERS = Map.of(
            "u", User
                    .withUsername("u")
                    .password("p")
                    .authorities("read")
                    .build(),
            "uu", User
                    .withUsername("uu")
                    .password("pp")
                    .authorities(() -> "read", () -> "write")
                    .passwordEncoder(s -> s)
                    .accountExpired(false)
                    .disabled(false)
                    .build()
    );

    @Bean
    public UserDetailsService userDetailsService() {
        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        USERS.values().forEach(inMemoryUserDetailsManager::createUser);
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
