package com.s06ssia.config;

import com.s06ssia.entity.AuthorityEntity;
import com.s06ssia.entity.ProductEntity;
import com.s06ssia.entity.UserEntity;
import com.s06ssia.entity.enums.AuthorityNameEnum;
import com.s06ssia.entity.enums.CurrencyEnum;
import com.s06ssia.entity.enums.EncryptionAlgorithmEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSetupCfg {

    private final EntityManager entityManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID().toString());
        productEntity.setName("Chocolate");
        productEntity.setPrice(10);
        productEntity.setCurrency(CurrencyEnum.USD);
        entityManager.persist(productEntity);

        var userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUsername("John");
        userEntity.setPassword(bCryptPasswordEncoder.encode("12345"));
        userEntity.setAlgorithm(EncryptionAlgorithmEnum.BCRYPT);
        entityManager.persist(userEntity);

        var readAuthorityEntity = new AuthorityEntity();
        readAuthorityEntity.setId(UUID.randomUUID().toString());
        readAuthorityEntity.setName(AuthorityNameEnum.READ);
        readAuthorityEntity.setUser(userEntity);
        entityManager.persist(readAuthorityEntity);

        var writeAuthorityEntity = new AuthorityEntity();
        writeAuthorityEntity.setId(UUID.randomUUID().toString());
        writeAuthorityEntity.setName(AuthorityNameEnum.WRITE);
        writeAuthorityEntity.setUser(userEntity);
        entityManager.persist(writeAuthorityEntity);

        userEntity.setAuthorities(List.of(readAuthorityEntity, writeAuthorityEntity));
        entityManager.persist(userEntity);
    }

}
