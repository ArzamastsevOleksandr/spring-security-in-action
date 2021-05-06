package com.s06ssia.entity;

import com.s06ssia.entity.enums.CurrencyEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
public class ProductEntity {

    @Id
    @Column(length = 36)
    private String id;

    private String name;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

}
