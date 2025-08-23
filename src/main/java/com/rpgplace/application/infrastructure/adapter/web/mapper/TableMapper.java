package com.rpgplace.application.infrastructure.adapter.web.mapper;

import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.TableRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.TableResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TableMapper {

    @Mapping(source = "masterId", target = "master.id")
    TableEntity toEntity(TableRequestDTO tableRequestDTO);

    @Mapping(source = "master.id", target = "masterId")
    TableResponseDTO toResponseDTO(TableEntity tableEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "master", ignore = true)
    @Mapping(target = "characters", ignore = true)
    void updateEntityFromDTO(TableRequestDTO tableRequestDTO, @MappingTarget TableEntity tableEntity);
}
