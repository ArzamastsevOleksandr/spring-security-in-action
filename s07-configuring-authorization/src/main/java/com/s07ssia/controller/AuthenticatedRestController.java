package com.s07ssia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/au")
@RequiredArgsConstructor
public class AuthenticatedRestController {

    @GetMapping("/a")
    public String a() {
        return "a";
    }

    @GetMapping("/b")
    public String b() {
        return "b";
    }

}
