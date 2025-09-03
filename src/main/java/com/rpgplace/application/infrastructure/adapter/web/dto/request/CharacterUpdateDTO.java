package com.rpgplace.application.infrastructure.adapter.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterUpdateDTO {
    private UUID id;
    private String name;
    private Integer health;
    private Integer mana;
    private Integer strength;
    private Integer agility;
    private Integer intelligence;
}
