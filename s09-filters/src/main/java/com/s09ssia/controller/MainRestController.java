package com.s09ssia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainRestController {

    @GetMapping("/m")
    public String manager() {
        return "m";
    }

}
