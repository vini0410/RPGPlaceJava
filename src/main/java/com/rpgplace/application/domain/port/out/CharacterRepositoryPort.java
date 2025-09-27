package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.CharacterEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

public interface CharacterRepositoryPort {

    @LogException(message = "Error saving character")
    CharacterEntity save(CharacterEntity characterEntity);

    @LogException(message = "Error finding character by id")
    Optional<CharacterEntity> findById(UUID id);

    @LogException(message = "Error finding all characters")
    List<CharacterEntity> findAll();

    @LogException(message = "Error finding characters by table id")
    List<CharacterEntity> findByTableId(UUID tableId);

    @LogException(message = "Error finding characters by user id")
    List<CharacterEntity> findByUserId(UUID userId);

    @LogException(message = "Error deleting character by id")
    void deleteById(UUID id);
}
