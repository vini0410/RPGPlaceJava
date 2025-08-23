package com.rpgplace.application.infrastructure.adapter.web.controller;


import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.CharacterMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterMapper characterMapper;
    // private final CharacterUseCase characterUseCase; // Placeholder for CharacterUseCase

    public CharacterController(CharacterMapper characterMapper /*, CharacterUseCase characterUseCase */) {
        this.characterMapper = characterMapper;
        // this.characterUseCase = characterUseCase;
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO characterRequestDTO) {
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);
        // CharacterEntity createdCharacter = characterUseCase.createCharacter(characterEntity);
        CharacterEntity createdCharacter = characterEntity; // Placeholder for actual use case call
        return new ResponseEntity<>(characterMapper.toResponseDTO(createdCharacter), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable UUID id) {
        // CharacterEntity characterEntity = characterUseCase.getCharacterById(id);
        CharacterEntity characterEntity = new CharacterEntity(); // Placeholder for actual use case call
        characterEntity.setId(id);
        // if (characterEntity == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(characterMapper.toResponseDTO(characterEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponseDTO>> getAllCharacters() {
        // List<CharacterEntity> characterEntities = characterUseCase.getAllCharacters();
        List<CharacterEntity> characterEntities = List.of(new CharacterEntity()); // Placeholder for actual use case call
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@PathVariable UUID id, @RequestBody CharacterRequestDTO characterRequestDTO) {
        // CharacterEntity existingCharacter = characterUseCase.getCharacterById(id);
        CharacterEntity existingCharacter = new CharacterEntity(); // Placeholder for actual use case call
        existingCharacter.setId(id);
        // if (existingCharacter == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        characterMapper.updateEntityFromDTO(characterRequestDTO, existingCharacter);
        // CharacterEntity updatedCharacter = characterUseCase.updateCharacter(existingCharacter);
        CharacterEntity updatedCharacter = existingCharacter; // Placeholder for actual use case call
        return new ResponseEntity<>(characterMapper.toResponseDTO(updatedCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable UUID id) {
        // characterUseCase.deleteCharacter(id);
        // Placeholder for actual use case call
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
