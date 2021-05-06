package com.s06ssia.entity;

import com.s06ssia.entity.enums.AuthorityNameEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
@EqualsAndHashCode(exclude = {
        "user"
})
@ToString(exclude = {
        "user"
})
public class AuthorityEntity {

    @Id
    @Column(length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    private AuthorityNameEnum name;

    @ManyToOne
    private UserEntity user;

}
