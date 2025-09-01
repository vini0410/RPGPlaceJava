package com.rpgplace.application.domain.port.in;

import com.rpgplace.application.domain.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserUseCasePort {

    UserEntity createUser(UserEntity userEntity);

    UserEntity getUserById(UUID id);

    List<UserEntity> getAllUsers();

    UserEntity updateUser(UUID id, UserEntity userEntity);

    void deleteUser(UUID id);
}
