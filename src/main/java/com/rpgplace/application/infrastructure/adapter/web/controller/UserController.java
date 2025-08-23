package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.UserRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.UserResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;
    // private final UserUseCase userUseCase; // Placeholder for UserUseCase

    public UserController(UserMapper userMapper /*, UserUseCase userUseCase */) {
        this.userMapper = userMapper;
        // this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userMapper.toEntity(userRequestDTO);
        // UserEntity createdUser = userUseCase.createUser(userEntity);
        UserEntity createdUser = userEntity; // Placeholder for actual use case call
        return new ResponseEntity<>(userMapper.toResponseDTO(createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        // UserEntity userEntity = userUseCase.getUserById(id);
        UserEntity userEntity = new UserEntity(); // Placeholder for actual use case call
        userEntity.setId(id);
        // if (userEntity == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(userMapper.toResponseDTO(userEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        // List<UserEntity> userEntities = userUseCase.getAllUsers();
        List<UserEntity> userEntities = List.of(new UserEntity()); // Placeholder for actual use case call
        List<UserResponseDTO> userResponseDTOs = userEntities.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
        // UserEntity existingUser = userUseCase.getUserById(id);
        UserEntity existingUser = new UserEntity(); // Placeholder for actual use case call
        existingUser.setId(id);
        // if (existingUser == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        userMapper.updateEntityFromDTO(userRequestDTO, existingUser);
        // UserEntity updatedUser = userUseCase.updateUser(existingUser);
        UserEntity updatedUser = existingUser; // Placeholder for actual use case call
        return new ResponseEntity<>(userMapper.toResponseDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        // userUseCase.deleteUser(id);
        // Placeholder for actual use case call
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
