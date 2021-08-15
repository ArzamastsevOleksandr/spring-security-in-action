package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    String home(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        log.debug("/home called by {}", oAuth2AuthenticationToken.getPrincipal());
        return "home.html";
    }

}
