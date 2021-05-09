package com.s07ssia.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .mvcMatchers("/m")
                .access("hasRole('MANAGER')")
                .mvcMatchers(HttpMethod.GET,"/a")
                .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/a")
                .permitAll();

        http
                .authorizeRequests()
                .mvcMatchers("/na")
                .permitAll();

        http
                .authorizeRequests()
                .mvcMatchers("/au/**")
                .authenticated();

        // note: prefer mvc instead of ant matchers.
        // mvc for /a will protect /a/ as well, while ant will not
        http
                .authorizeRequests()
                .mvcMatchers("/path/{val:^[0-9]*$}")
                .permitAll()
                .mvcMatchers("/path/par/{par}")
                .hasRole("ADMIN");

        http
                .authorizeRequests()
                .mvcMatchers("/email/{email:.*(.+@.+\\.com)}")
                .permitAll()
                .mvcMatchers("/video/{country:(us|uk|ca)}/{language:(en|fr)}")
                .permitAll()
//                .anyRequest() // this will bypass the above configs and will allow even the /video/bla/bla request
//                .hasAuthority("PREMIUM")
                .anyRequest()
                .denyAll();

//        http
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();
//                .hasAuthority(AuthorityNameEnum.WRITE.toString());
//                .access("hasAuthority('WRITE') and !hasAuthority('DELETE')");
        http
                .csrf()
                .disable();
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
