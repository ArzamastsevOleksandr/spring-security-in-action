package com.ssia.config;

import com.ssia.entity.AuthorityEntity;
import com.ssia.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@Profile("local")
@Configuration
@RequiredArgsConstructor
public class UserSetupCfg {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    @Transactional
    public void on(ApplicationReadyEvent event) {
        var userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUsername("u");
        userEntity.setPassword(passwordEncoder.encode("p"));
        entityManager.persist(userEntity);

        var authorityEntity = new AuthorityEntity();
        authorityEntity.setId(UUID.randomUUID().toString());
        authorityEntity.setUsername("u");
        authorityEntity.setAuthority("write");
        entityManager.persist(authorityEntity);
    }

}
