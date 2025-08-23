package com.rpgplace.application.infrastructure.adapter.web.controller;

import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.TableRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.TableResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.TableMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final TableMapper tableMapper;
    // private final TableUseCase tableUseCase; // Placeholder for TableUseCase

    public TableController(TableMapper tableMapper /*, TableUseCase tableUseCase */) {
        this.tableMapper = tableMapper;
        // this.tableUseCase = tableUseCase;
    }

    @PostMapping
    public ResponseEntity<TableResponseDTO> createTable(@RequestBody TableRequestDTO tableRequestDTO) {
        TableEntity tableEntity = tableMapper.toEntity(tableRequestDTO);
        // TableEntity createdTable = tableUseCase.createTable(tableEntity);
        TableEntity createdTable = tableEntity; // Placeholder for actual use case call
        return new ResponseEntity<>(tableMapper.toResponseDTO(createdTable), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableResponseDTO> getTableById(@PathVariable UUID id) {
        // TableEntity tableEntity = tableUseCase.getTableById(id);
        TableEntity tableEntity = new TableEntity(); // Placeholder for actual use case call
        tableEntity.setId(id);
        // if (tableEntity == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(tableMapper.toResponseDTO(tableEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TableResponseDTO>> getAllTables() {
        // List<TableEntity> tableEntities = tableUseCase.getAllTables();
        List<TableEntity> tableEntities = List.of(new TableEntity()); // Placeholder for actual use case call
        List<TableResponseDTO> tableResponseDTOs = tableEntities.stream()
                .map(tableMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tableResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableResponseDTO> updateTable(@PathVariable UUID id, @RequestBody TableRequestDTO tableRequestDTO) {
        // TableEntity existingTable = tableUseCase.getTableById(id);
        TableEntity existingTable = new TableEntity(); // Placeholder for actual use case call
        existingTable.setId(id);
        // if (existingTable == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        tableMapper.updateEntityFromDTO(tableRequestDTO, existingTable);
        // TableEntity updatedTable = tableUseCase.updateTable(existingTable);
        TableEntity updatedTable = existingTable; // Placeholder for actual use case call
        return new ResponseEntity<>(tableMapper.toResponseDTO(updatedTable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable UUID id) {
        // tableUseCase.deleteTable(id);
        // Placeholder for actual use case call
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
