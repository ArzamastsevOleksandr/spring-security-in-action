package com.s07ssia.entity;

import com.s07ssia.entity.enums.EncryptionAlgorithmEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities;

}
