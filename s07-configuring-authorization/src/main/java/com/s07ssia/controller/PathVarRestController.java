package com.s07ssia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/path")
@RequiredArgsConstructor
public class PathVarRestController {

    @GetMapping("/{val}")
    public String numericVal(@PathVariable("val") Integer val) {
        return "path/" + val;
    }

    @GetMapping("/par/{par}")
    public String par(@PathVariable("par") String par) {
        return "path/" + par;
    }

}
