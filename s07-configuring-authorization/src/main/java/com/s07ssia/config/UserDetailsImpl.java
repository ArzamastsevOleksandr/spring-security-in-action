package com.s07ssia.config;

import com.s07ssia.entity.UserEntity;
import com.s07ssia.entity.enums.EncryptionAlgorithmEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final UserEntity userEntity;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.authorities = userEntity.getRoles()
                .stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getName().toString()))
                .collect(toList());
    }

    public EncryptionAlgorithmEnum getAlgorithm() {
        return userEntity.getAlgorithm();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
