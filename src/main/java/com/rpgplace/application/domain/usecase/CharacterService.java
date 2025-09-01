package com.rpgplace.application.domain.usecase;

import com.rpgplace.application.domain.exception.ResourceNotFoundException;
import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.out.CharacterRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharacterService implements CharacterUseCasePort {

    private final CharacterRepositoryPort characterRepositoryPort;

    @Override
    public CharacterEntity createCharacter(CharacterEntity characterEntity) {
        return characterRepositoryPort.save(characterEntity);
    }

    @Override
    public CharacterEntity getCharacterById(UUID id) {
        return characterRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
    }

    @Override
    public List<CharacterEntity> getAllCharacters() {
        return characterRepositoryPort.findAll();
    }

    @Override
    public List<CharacterEntity> findCharactersByTable(UUID tableId) {
        return characterRepositoryPort.findByTableId(tableId);
    }

    @Override
    public List<CharacterEntity> findCharactersByUser(UUID userId) {
        return characterRepositoryPort.findByUserId(userId);
    }

    @Override
    public CharacterEntity updateCharacter(UUID id, CharacterEntity characterEntity) {
        CharacterEntity existingCharacter = characterRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));

        existingCharacter.setName(characterEntity.getName());
        existingCharacter.setHealth(characterEntity.getHealth());
        existingCharacter.setMana(characterEntity.getMana());
        existingCharacter.setStrength(characterEntity.getStrength());
        existingCharacter.setAgility(characterEntity.getAgility());
        existingCharacter.setIntelligence(characterEntity.getIntelligence());
        existingCharacter.setTable(characterEntity.getTable());
        existingCharacter.setUser(characterEntity.getUser());

        return characterRepositoryPort.save(existingCharacter);
    }

    @Override
    public void deleteCharacter(UUID id) {
        characterRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
        characterRepositoryPort.deleteById(id);
    }
}
