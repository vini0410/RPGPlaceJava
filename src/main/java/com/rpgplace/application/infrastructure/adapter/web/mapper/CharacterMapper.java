package com.rpgplace.application.infrastructure.adapter.web.mapper;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "tableId", target = "table.id")
    CharacterEntity toEntity(CharacterRequestDTO characterRequestDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "table.id", target = "tableId")
    CharacterResponseDTO toResponseDTO(CharacterEntity characterEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "table", ignore = true)
    void updateEntityFromDTO(CharacterRequestDTO characterRequestDTO, @MappingTarget CharacterEntity characterEntity);
}
