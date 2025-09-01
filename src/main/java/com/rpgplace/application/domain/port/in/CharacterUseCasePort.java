package com.rpgplace.application.domain.port.in;

import com.rpgplace.application.domain.model.CharacterEntity;

import java.util.List;
import java.util.UUID;

public interface CharacterUseCasePort {

    CharacterEntity createCharacter(CharacterEntity characterEntity);

    CharacterEntity getCharacterById(UUID id);

    List<CharacterEntity> getAllCharacters();

    List<CharacterEntity> findCharactersByTable(UUID tableId);

    List<CharacterEntity> findCharactersByUser(UUID userId);

    CharacterEntity updateCharacter(UUID id, CharacterEntity characterEntity);

    void deleteCharacter(UUID id);
}
