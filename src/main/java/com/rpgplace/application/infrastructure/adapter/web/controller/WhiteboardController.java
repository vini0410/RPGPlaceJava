package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.infrastructure.adapter.web.dto.request.DrawEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WhiteboardController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/table/{tableId}/draw")
    public void handleDrawEvent(@DestinationVariable String tableId, DrawEventDTO drawEvent) {
        String destination = String.format("/topic/table/%s/draw", tableId);
        messagingTemplate.convertAndSend(destination, drawEvent);
    }
}
