package com.ssia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "userDetailsServiceCustom")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier(value = "noOpPasswordEncoder")
    private PasswordEncoder noOpPasswordEncoder;

    @Autowired
    @Qualifier(value = "plainTextPasswordEncoder")
    private PasswordEncoder plainTextPasswordEncoder;

    @Resource
    private PasswordEncoder sha512PasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(sha512PasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
    }

}
