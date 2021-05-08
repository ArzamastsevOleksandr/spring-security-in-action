package com.s07ssia.config;

import com.s07ssia.entity.AuthorityEntity;
import com.s07ssia.entity.UserEntity;
import com.s07ssia.entity.enums.AuthorityNameEnum;
import com.s07ssia.entity.enums.EncryptionAlgorithmEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSetupCfg {

    private final EntityManager entityManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Transactional
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var adminUserEntity = new UserEntity();
        adminUserEntity.setId(UUID.randomUUID().toString());
        adminUserEntity.setUsername("John");
        adminUserEntity.setPassword(bCryptPasswordEncoder.encode("12345"));
        adminUserEntity.setAlgorithm(EncryptionAlgorithmEnum.BCRYPT);
        entityManager.persist(adminUserEntity);

        var readUserEntity = new UserEntity();
        readUserEntity.setId(UUID.randomUUID().toString());
        readUserEntity.setUsername("Kal");
        readUserEntity.setPassword(sCryptPasswordEncoder.encode("12345"));
        readUserEntity.setAlgorithm(EncryptionAlgorithmEnum.SCRYPT);
        entityManager.persist(readUserEntity);

        var readAuthorityEntity = new AuthorityEntity();
        readAuthorityEntity.setId(UUID.randomUUID().toString());
        readAuthorityEntity.setName(AuthorityNameEnum.READ);
        readAuthorityEntity.setUser(adminUserEntity);
        entityManager.persist(readAuthorityEntity);

        var writeAuthorityEntity = new AuthorityEntity();
        writeAuthorityEntity.setId(UUID.randomUUID().toString());
        writeAuthorityEntity.setName(AuthorityNameEnum.WRITE);
        writeAuthorityEntity.setUser(adminUserEntity);
        entityManager.persist(writeAuthorityEntity);

        adminUserEntity.setAuthorities(List.of(readAuthorityEntity, writeAuthorityEntity));
        entityManager.persist(adminUserEntity);

        readUserEntity.setAuthorities(List.of(readAuthorityEntity));
        entityManager.persist(readUserEntity);
    }

}
