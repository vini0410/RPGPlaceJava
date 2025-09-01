package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.CharacterEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterRepositoryPort {

    CharacterEntity save(CharacterEntity characterEntity);

    Optional<CharacterEntity> findById(UUID id);

    List<CharacterEntity> findAll();

    List<CharacterEntity> findByTableId(UUID tableId);

    List<CharacterEntity> findByUserId(UUID userId);

    void deleteById(UUID id);
}
