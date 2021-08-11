package com.example.demo.service;

import com.example.demo.dto.OtpDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.OtpEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.GenerateCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final EntityManager entityManager;

    @Transactional
    public void addUser(UserDto userDto) {
        var userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        entityManager.persist(userEntity);
    }

    @Transactional
    public void auth(UserDto userDto) {
        userRepository.findByUsername(userDto.getUsername())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .ifPresentOrElse(this::renewOpt, () -> {
                    throw new BadCredentialsException("Bad credentials");
                });
    }

    public boolean check(OtpDto otpDto) {
        return otpRepository.findByUsername(otpDto.getUsername())
                .filter(otpEntity -> Objects.equals(otpEntity.getOtp(), otpDto.getOtp()))
                .isPresent();
    }

    private void renewOpt(UserEntity userEntity) {
        String otp = GenerateCodeUtil.generateOtp();

        otpRepository.findByUsername(userEntity.getUsername())
                .ifPresentOrElse(otpEntity -> {
                    otpEntity.setOtp(otp);
                    entityManager.persist(otpEntity);
                }, () -> {
                    var otpEntity = new OtpEntity();
                    otpEntity.setUsername(userEntity.getUsername());
                    otpEntity.setOtp(otp);
                    entityManager.persist(otpEntity);
                });
    }

}
