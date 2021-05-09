package com.s07ssia.entity;

import com.s07ssia.entity.enums.EncryptionAlgorithmEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithmEnum algorithm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

}
