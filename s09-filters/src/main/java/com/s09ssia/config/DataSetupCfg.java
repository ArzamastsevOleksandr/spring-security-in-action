package com.s09ssia.config;

import com.s09ssia.entity.RoleEntity;
import com.s09ssia.entity.UserEntity;
import com.s09ssia.entity.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSetupCfg {

    private final EntityManager entityManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var adminRoleEntity = new RoleEntity();
        adminRoleEntity.setId(UUID.randomUUID().toString());
        adminRoleEntity.setName(RoleEnum.ROLE_ADMIN);
        entityManager.persist(adminRoleEntity);

//        var managerRoleEntity = new RoleEntity();
//        managerRoleEntity.setId(UUID.randomUUID().toString());
//        managerRoleEntity.setName(RoleNameEnum.ROLE_MANAGER);
//        entityManager.persist(managerRoleEntity);

        var adminUserEntity = new UserEntity();
        adminUserEntity.setId(UUID.randomUUID().toString());
        adminUserEntity.setUsername("a");
        adminUserEntity.setPassword(bCryptPasswordEncoder.encode("p"));
        adminUserEntity.setRoles(Set.of(adminRoleEntity));
        entityManager.persist(adminUserEntity);
    }

}
