package com.s07ssia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainRestController {

    @GetMapping("/m")
    public String manager() {
        return "manager_role";
    }

    @GetMapping("/a")
    public String adminGet() {
        return "admin_role_get";
    }

    @PostMapping("/a")
    public String post() {
        return "a_post";
    }

    @GetMapping("/n")
    public String noRole() {
        return "no_role";
    }

    @GetMapping("/na")
    public String noAuth() {
        return "no_auth";
    }

}
