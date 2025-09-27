package com.rpgplace.application.infrastructure.adapter.web.controller.rest;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.CharacterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterMapper characterMapper;
    private final CharacterUseCasePort characterUseCasePort;

    @PostMapping
    public ResponseEntity<CharacterResponseDTO> createCharacter(@Valid @RequestBody CharacterRequestDTO characterRequestDTO) {
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);
        CharacterEntity createdCharacter = characterUseCasePort.createCharacter(characterEntity);
        return new ResponseEntity<>(characterMapper.toResponseDTO(createdCharacter), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable UUID id) {
        CharacterEntity characterEntity = characterUseCasePort.getCharacterById(id);
        return new ResponseEntity<>(characterMapper.toResponseDTO(characterEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponseDTO>> getAllCharacters() {
        List<CharacterEntity> characterEntities = characterUseCasePort.getAllCharacters();
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/my-characters")
    public ResponseEntity<List<CharacterResponseDTO>> getMyCharacters(@AuthenticationPrincipal UserEntity user) {
        List<CharacterEntity> characterEntities = characterUseCasePort.findCharactersByUser(user.getId());
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@PathVariable UUID id, @Valid @RequestBody CharacterRequestDTO characterRequestDTO) {
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);
        CharacterEntity updatedCharacter = characterUseCasePort.updateCharacter(id, characterEntity);
        return new ResponseEntity<>(characterMapper.toResponseDTO(updatedCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable UUID id) {
        characterUseCasePort.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}