package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.in.TableUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterUpdateDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.CharacterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterMapper characterMapper;
    private final CharacterUseCasePort characterUseCasePort;
    private final SimpMessagingTemplate messagingTemplate;
    private final TableUseCasePort tableUseCasePort;


    @MessageMapping("/table/{tableId}/character/update")
    public void updateCharacterSocket(
            @DestinationVariable String tableId,
            CharacterUpdateDTO characterUpdateDTO,
            StompHeaderAccessor accessor) {

        UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) accessor.getUser();
        UserEntity principal = (UserEntity) userAuth.getPrincipal();

        CharacterEntity updatedEntity = characterUseCasePort.updateCharacterSocket(characterUpdateDTO, principal);
        CharacterResponseDTO responseDTO = characterMapper.toResponseDTO(updatedEntity);

        String destination = String.format("/topic/table/%s/characters", tableId);
        messagingTemplate.convertAndSend(destination, responseDTO);
    }

    @MessageMapping("/table/{tableId}/characters/getall")
    public void getAllCharactersForTable(
            @DestinationVariable UUID tableId,
            StompHeaderAccessor accessor) {

        UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) accessor.getUser();
        UserEntity principal = (UserEntity) userAuth.getPrincipal();

        System.out.println("Principal: " + principal);
        System.out.println("Principal Username: " + (principal != null ? principal.getUsername() : "null"));

        List<CharacterEntity> characterEntities = characterUseCasePort.findCharactersByTable(tableId);
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());

        System.out.println("Characters to send: " + characterResponseDTOs);

        String userDestination = String.format("/user/%s/queue/table/%s/characters", principal.getUsername(), tableId);
        System.out.println("Sending to destination: " + userDestination);
        messagingTemplate.convertAndSend(userDestination, characterResponseDTOs);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<CharacterResponseDTO> createCharacter(@Valid @RequestBody CharacterRequestDTO characterRequestDTO) {
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);
        CharacterEntity createdCharacter = characterUseCasePort.createCharacter(characterEntity);
        return new ResponseEntity<>(characterMapper.toResponseDTO(createdCharacter), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable UUID id) {
        CharacterEntity characterEntity = characterUseCasePort.getCharacterById(id);
        return new ResponseEntity<>(characterMapper.toResponseDTO(characterEntity), HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CharacterResponseDTO>> getAllCharacters() {
        List<CharacterEntity> characterEntities = characterUseCasePort.getAllCharacters();
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/my-characters")
    @ResponseBody
    public ResponseEntity<List<CharacterResponseDTO>> getMyCharacters(@AuthenticationPrincipal UserEntity user) {
        List<CharacterEntity> characterEntities = characterUseCasePort.findCharactersByUser(user.getId());
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@PathVariable UUID id, @Valid @RequestBody CharacterRequestDTO characterRequestDTO) {
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);
        CharacterEntity updatedCharacter = characterUseCasePort.updateCharacter(id, characterEntity);
        return new ResponseEntity<>(characterMapper.toResponseDTO(updatedCharacter), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCharacter(@PathVariable UUID id) {
        characterUseCasePort.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}