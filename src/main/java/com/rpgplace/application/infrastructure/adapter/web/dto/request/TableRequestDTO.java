package com.rpgplace.application.infrastructure.adapter.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableRequestDTO {
    private String title;
    private String rulebook;
    private String accessCode;
    private UUID masterId;
}
