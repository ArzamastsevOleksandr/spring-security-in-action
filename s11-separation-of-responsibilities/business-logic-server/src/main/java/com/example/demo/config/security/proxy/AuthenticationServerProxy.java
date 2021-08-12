package com.example.demo.config.security.proxy;

import com.example.demo.config.security.dto.OtpDto;
import com.example.demo.config.security.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {

    private final RestTemplate restTemplate;

    @Value("${auth.server.base.url:http://localhost:8090}")
    private String baseUrl;

    public void sendAuth(String username, String password) {
        var userDto = UserDto.builder()
                .username(username)
                .password(password)
                .build();
        restTemplate.postForEntity(baseUrl + "/user/auth", new HttpEntity<>(userDto), Void.class);
    }

    public boolean sendOtp(String username, String otp) {
        var otpDto = OtpDto.builder()
                .username(username)
                .otp(otp)
                .build();
        var response = restTemplate.postForEntity(baseUrl + "/otp/check", new HttpEntity<>(otpDto), Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

}
