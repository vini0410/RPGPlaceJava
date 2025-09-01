package com.rpgplace.application.infrastructure.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDTO {

    @NotBlank(message = "Access code cannot be blank")
    private String accessCode;
}
