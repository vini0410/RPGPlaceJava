package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.UserUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.UserUpdateRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.UserRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.UserResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserUseCasePort userUseCasePort;

    

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserEntity userEntity = userUseCasePort.getUserById(id);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.toResponseDTO(userEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserEntity> userEntities = userUseCasePort.getAllUsers();
        List<UserResponseDTO> userResponseDTOs = userEntities.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDTOs, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO userRequestDTO) {
        UserEntity userEntity = userMapper.toEntity(userRequestDTO);
        UserEntity updatedUser = userUseCasePort.updateUser(id, userEntity);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.toResponseDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userUseCasePort.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
