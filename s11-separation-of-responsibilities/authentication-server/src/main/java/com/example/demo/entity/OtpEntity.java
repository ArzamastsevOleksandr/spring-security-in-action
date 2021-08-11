package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "otp")
@Data
public class OtpEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String username;
    private String otp;

    public OtpEntity() {
        id = UUID.randomUUID().toString();
    }

}
