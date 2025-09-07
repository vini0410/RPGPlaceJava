package com.rpgplace.application.infrastructure.controller;

import com.rpgplace.application.domain.dto.ChatMessage;
import com.rpgplace.application.domain.dto.ChatMessageDTO;
import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.in.TableUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CharacterUseCasePort characterUseCasePort;
    private final TableUseCasePort tableUseCasePort;

    @MessageMapping("/table/{tableId}/chat/send")
    public void sendChatMessage(@DestinationVariable String tableId, @Payload ChatMessageDTO chatMessageDTO, Principal principal) {
        UserEntity user = (UserEntity) ((Authentication) principal).getPrincipal();
        TableEntity table = tableUseCasePort.getTableById(UUID.fromString(tableId));

        String characterName;
        if (user.getId().equals(table.getMaster().getId())) {
            characterName = "Master";
        } else {
            List<CharacterEntity> characters = characterUseCasePort.findCharactersByTable(UUID.fromString(tableId));
            characterName = characters.stream()
                    .filter(c -> c.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .map(CharacterEntity::getName)
                    .orElse("Unknown");
        }

        ChatMessage chatMessage = new ChatMessage(
                user.getId().toString(),
                user.getName(),
                characterName,
                chatMessageDTO.getText(),
                OffsetDateTime.now()
        );

        messagingTemplate.convertAndSend("/topic/table/" + tableId + "/chat", chatMessage);
    }
}