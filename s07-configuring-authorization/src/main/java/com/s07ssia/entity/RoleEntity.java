package com.s07ssia.entity;

import com.s07ssia.entity.enums.RoleNameEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {

    @Id
    @Column(length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    private RoleNameEnum name;

}
