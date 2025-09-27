package com.rpgplace.application.infrastructure.adapter.web.controller.ws;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterUpdateDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.CharacterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CharacterWSController {

    private final CharacterMapper characterMapper;
    private final CharacterUseCasePort characterUseCasePort;
    private final SimpMessagingTemplate messagingTemplate;

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
}