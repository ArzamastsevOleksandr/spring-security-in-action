package com.s07ssia.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProviderService authenticationProviderService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(authenticationProviderService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic();
        http
                .authorizeRequests()
                .anyRequest()
//                .hasAuthority(AuthorityNameEnum.WRITE.toString());
                .access("hasAuthority('WRITE') and !hasAuthority('DELETE')");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

}
