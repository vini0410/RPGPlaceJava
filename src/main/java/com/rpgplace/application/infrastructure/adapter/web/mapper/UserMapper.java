package com.rpgplace.application.infrastructure.adapter.web.mapper;

import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.UserRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponseDTO(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(UserRequestDTO userRequestDTO, @MappingTarget UserEntity userEntity);
}
