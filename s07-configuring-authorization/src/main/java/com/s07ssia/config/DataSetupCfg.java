package com.s07ssia.config;

import com.s07ssia.entity.RoleEntity;
import com.s07ssia.entity.UserEntity;
import com.s07ssia.entity.enums.EncryptionAlgorithmEnum;
import com.s07ssia.entity.enums.RoleNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Set;
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
        var adminRoleEntity = new RoleEntity();
        adminRoleEntity.setId(UUID.randomUUID().toString());
        adminRoleEntity.setName(RoleNameEnum.ROLE_ADMIN);
        entityManager.persist(adminRoleEntity);

        var managerRoleEntity = new RoleEntity();
        managerRoleEntity.setId(UUID.randomUUID().toString());
        managerRoleEntity.setName(RoleNameEnum.ROLE_MANAGER);
        entityManager.persist(managerRoleEntity);

        var adminUserEntity = new UserEntity();
        adminUserEntity.setId(UUID.randomUUID().toString());
        adminUserEntity.setUsername("a");
        adminUserEntity.setPassword(bCryptPasswordEncoder.encode("p"));
        adminUserEntity.setAlgorithm(EncryptionAlgorithmEnum.BCRYPT);
        adminUserEntity.setRoles(Set.of(adminRoleEntity));
        entityManager.persist(adminUserEntity);

        var managerUserEntity = new UserEntity();
        managerUserEntity.setId(UUID.randomUUID().toString());
        managerUserEntity.setUsername("m");
        managerUserEntity.setPassword(sCryptPasswordEncoder.encode("p"));
        managerUserEntity.setAlgorithm(EncryptionAlgorithmEnum.SCRYPT);
        managerUserEntity.setRoles(Set.of(managerRoleEntity));
        entityManager.persist(managerUserEntity);

        var noRoleUserEntity = new UserEntity();
        noRoleUserEntity.setId(UUID.randomUUID().toString());
        noRoleUserEntity.setUsername("n");
        noRoleUserEntity.setPassword(sCryptPasswordEncoder.encode("p"));
        noRoleUserEntity.setAlgorithm(EncryptionAlgorithmEnum.SCRYPT);
        noRoleUserEntity.setRoles(Set.of());
        entityManager.persist(noRoleUserEntity);
    }

}
