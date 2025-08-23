package com.rpgplace.application.infrastructure.adapter.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDTO {
    private UUID id;
    private String title;
    private String rulebook;
    private String accessCode;
    private UUID masterId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
