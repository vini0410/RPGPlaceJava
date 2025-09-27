package com.rpgplace.application.domain.usecase;

import com.rpgplace.application.domain.exception.ResourceNotFoundException;
import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.out.CharacterRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

@Service
@RequiredArgsConstructor
public class CharacterService implements CharacterUseCasePort {

    private final CharacterRepositoryPort characterRepositoryPort;

    @Override
    @LogException(message = "Error creating character")
    public CharacterEntity createCharacter(CharacterEntity characterEntity) {
        return characterRepositoryPort.save(characterEntity);
    }

    @Override
    @LogException(message = "Error getting character by id")
    public CharacterEntity getCharacterById(UUID id) {
        return characterRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
    }

    @Override
    @LogException(message = "Error getting all characters")
    public List<CharacterEntity> getAllCharacters() {
        return characterRepositoryPort.findAll();
    }

    @Override
    @LogException(message = "Error finding characters by table")
    public List<CharacterEntity> findCharactersByTable(UUID tableId) {
        return characterRepositoryPort.findByTableId(tableId);
    }

    @Override
    @LogException(message = "Error finding characters by user")
    public List<CharacterEntity> findCharactersByUser(UUID userId) {
        return characterRepositoryPort.findByUserId(userId);
    }

    @Override
    @LogException(message = "Error updating character")
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
    @LogException(message = "Error deleting character")
    public void deleteCharacter(UUID id) {
        characterRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character not found with id: " + id));
        characterRepositoryPort.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    @LogException(message = "Error updating character via socket")
    public com.rpgplace.application.domain.model.CharacterEntity updateCharacterSocket(com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterUpdateDTO characterUpdateDTO, com.rpgplace.application.domain.model.UserEntity principal) {
        com.rpgplace.application.domain.model.CharacterEntity characterToUpdate = getCharacterById(characterUpdateDTO.getId());
        com.rpgplace.application.domain.model.TableEntity table = characterToUpdate.getTable();

        boolean isMaster = table.getMaster().getId().equals(principal.getId());
        boolean isOwner = characterToUpdate.getUser().getId().equals(principal.getId());

        if (!isMaster && !isOwner) {
            throw new org.springframework.security.access.AccessDeniedException("You are not authorized to update this character.");
        }

        characterToUpdate.setName(characterUpdateDTO.getName());
        characterToUpdate.setHealth(characterUpdateDTO.getHealth());
        characterToUpdate.setMana(characterUpdateDTO.getMana());
        characterToUpdate.setStrength(characterUpdateDTO.getStrength());
        characterToUpdate.setAgility(characterUpdateDTO.getAgility());
        characterToUpdate.setIntelligence(characterUpdateDTO.getIntelligence());

        return characterRepositoryPort.save(characterToUpdate);
    }
}
