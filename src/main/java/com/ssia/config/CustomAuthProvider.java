package com.ssia.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.ssia.config.UserManagementSecurityCfg.USERS;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        if (USERS.containsKey(username)) {
            UserDetails userDetails = USERS.get(username);
            if (userDetails.getPassword().equals(password)) {
                return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
            }
        }
        throw new AuthenticationCredentialsNotFoundException("Username/Password is incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

}
