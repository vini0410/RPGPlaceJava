package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.in.TableUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.CharacterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.JoinRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.TableRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.CharacterResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.TableResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.CharacterMapper;
import com.rpgplace.application.infrastructure.adapter.web.mapper.TableMapper;
import com.rpgplace.application.infrastructure.aop.LogException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableMapper tableMapper;
    private final TableUseCasePort tableUseCasePort;
    private final CharacterMapper characterMapper;
    private final CharacterUseCasePort characterUseCasePort;

    @PostMapping
    @LogException(message = "Error creating table")
    public ResponseEntity<TableResponseDTO> createTable(@Valid @RequestBody TableRequestDTO tableRequestDTO) {
        TableEntity tableEntity = tableMapper.toEntity(tableRequestDTO);
        TableEntity createdTable = tableUseCasePort.createTable(tableEntity);
        return new ResponseEntity<>(tableMapper.toResponseDTO(createdTable), HttpStatus.CREATED);
    }

    @GetMapping("/owned")
    @LogException(message = "Error getting owned tables")
    public ResponseEntity<List<TableResponseDTO>> getOwnedTables(@AuthenticationPrincipal UserEntity user) {
        List<TableEntity> tableEntities = tableUseCasePort.findTablesByMaster(user.getId());
        List<TableResponseDTO> tableResponseDTOs = tableEntities.stream()
                .map(tableMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tableResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/joined")
    @LogException(message = "Error getting joined tables")
    public ResponseEntity<List<TableResponseDTO>> getJoinedTables(@AuthenticationPrincipal UserEntity user) {
        List<TableEntity> tableEntities = tableUseCasePort.findTablesByPlayer(user.getId());
        List<TableResponseDTO> tableResponseDTOs = tableEntities.stream()
                .map(tableMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tableResponseDTOs, HttpStatus.OK);
    }

    @PostMapping("/join")
    @LogException(message = "Error joining table")
    public ResponseEntity<TableResponseDTO> joinTable(@Valid @RequestBody JoinRequestDTO joinRequestDTO) {
        TableEntity tableEntity = tableUseCasePort.findTableByAccessCode(joinRequestDTO.getAccessCode());
        return new ResponseEntity<>(tableMapper.toResponseDTO(tableEntity), HttpStatus.OK);
    }

    @GetMapping("/{tableId}/characters")
    @LogException(message = "Error getting characters by table id")
    public ResponseEntity<List<CharacterResponseDTO>> getCharactersByTableId(@PathVariable UUID tableId) {
        List<CharacterEntity> characterEntities = characterUseCasePort.findCharactersByTable(tableId);
        List<CharacterResponseDTO> characterResponseDTOs = characterEntities.stream()
                .map(characterMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterResponseDTOs, HttpStatus.OK);
    }

    @PostMapping("/{tableId}/characters")
    @LogException(message = "Error creating character for table")
    public ResponseEntity<CharacterResponseDTO> createCharacterForTable(
            @PathVariable UUID tableId,
            @Valid @RequestBody CharacterRequestDTO characterRequestDTO) {

        characterRequestDTO.setTableId(tableId);
        CharacterEntity characterEntity = characterMapper.toEntity(characterRequestDTO);

        CharacterEntity createdCharacter = characterUseCasePort.createCharacter(characterEntity);
        return new ResponseEntity<>(characterMapper.toResponseDTO(createdCharacter), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @LogException(message = "Error getting table by id")
    public ResponseEntity<TableResponseDTO> getTableById(@PathVariable UUID id) {
        TableEntity tableEntity = tableUseCasePort.getTableById(id);
        return new ResponseEntity<>(tableMapper.toResponseDTO(tableEntity), HttpStatus.OK);
    }

    @GetMapping
    @LogException(message = "Error getting all tables")
    public ResponseEntity<List<TableResponseDTO>> getAllTables() {
        List<TableEntity> tableEntities = tableUseCasePort.getAllTables();
        List<TableResponseDTO> tableResponseDTOs = tableEntities.stream()
                .map(tableMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tableResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @LogException(message = "Error updating table")
    public ResponseEntity<TableResponseDTO> updateTable(@PathVariable UUID id, @Valid @RequestBody TableRequestDTO tableRequestDTO) {
        TableEntity tableEntity = tableMapper.toEntity(tableRequestDTO);
        TableEntity updatedTable = tableUseCasePort.updateTable(id, tableEntity);
        return new ResponseEntity<>(tableMapper.toResponseDTO(updatedTable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @LogException(message = "Error deleting table")
    public ResponseEntity<Void> deleteTable(@PathVariable UUID id) {
        tableUseCasePort.deleteTable(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
