package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

public interface UserRepositoryPort {

    @LogException(message = "Error saving user")
    UserEntity save(UserEntity userEntity);

    @LogException(message = "Error finding user by id")
    Optional<UserEntity> findById(UUID id);

    @LogException(message = "Error finding user by email")
    Optional<UserEntity> findByEmail(String email);

    @LogException(message = "Error finding all users")
    List<UserEntity> findAll();

    @LogException(message = "Error deleting user by id")
    void deleteById(UUID id);
}
