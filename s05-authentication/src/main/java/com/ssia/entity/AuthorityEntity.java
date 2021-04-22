package com.ssia.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@Data
public class AuthorityEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String username;
    private String authority;

}
