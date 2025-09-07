package com.rpgplace.application.infrastructure.adapter.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawEventDTO {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String color;
    private int size;
}
