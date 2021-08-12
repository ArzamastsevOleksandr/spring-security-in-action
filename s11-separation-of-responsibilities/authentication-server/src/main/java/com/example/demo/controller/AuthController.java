package com.example.demo.controller;

import com.example.demo.dto.OtpDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/user/add")
    void addUser(@RequestBody UserDto userDto) {
        log.debug("addUser: {}", userDto);
        userService.addUser(userDto);
    }

    @PostMapping("/user/auth")
    void auth(@RequestBody UserDto userDto) {
        log.debug("auth: {}", userDto);
        userService.auth(userDto);
    }

    @PostMapping("/otp/check")
    void auth(@RequestBody OtpDto otpDto, HttpServletResponse response) {
        log.debug("check: {}", otpDto);
        if (userService.check(otpDto)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
