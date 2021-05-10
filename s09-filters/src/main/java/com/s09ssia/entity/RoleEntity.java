package com.s09ssia.entity;

import com.s09ssia.entity.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {

    @Id
    @Column(length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
