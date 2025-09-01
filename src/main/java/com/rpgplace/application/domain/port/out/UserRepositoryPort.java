package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    void deleteById(UUID id);
}
