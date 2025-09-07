package com.rpgplace.application.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String senderId;
    private String senderName;
    private String characterName;
    private String text;
    private OffsetDateTime timestamp;
}
