package com.example.demo.repository;

import com.example.demo.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, String> {

    Optional<OtpEntity> findByUsername(String username);

}
