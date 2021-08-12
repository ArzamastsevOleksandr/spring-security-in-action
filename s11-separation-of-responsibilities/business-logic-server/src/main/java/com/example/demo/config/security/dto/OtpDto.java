package com.example.demo.config.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpDto {

    private String username;
    private String otp;

}
