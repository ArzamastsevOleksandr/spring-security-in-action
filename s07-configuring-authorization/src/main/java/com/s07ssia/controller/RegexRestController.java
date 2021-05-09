package com.s07ssia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegexRestController {

    @GetMapping("/video/{country}/{language}")
    public String numericVal(@PathVariable String country, @PathVariable String language) {
        return "/video/" + country + "/" + language;
    }

    @GetMapping("/email/{email}")
    public String email(@PathVariable String email) {
        return "/email/" + email;
    }

}
