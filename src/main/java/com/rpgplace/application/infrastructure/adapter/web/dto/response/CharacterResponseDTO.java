package com.rpgplace.application.infrastructure.adapter.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponseDTO {
    private UUID id;
    private String name;
    private Integer health;
    private Integer mana;
    private Integer strength;
    private Integer agility;
    private Integer intelligence;
    private UUID userId;
    private UUID tableId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
