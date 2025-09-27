package com.rpgplace.application.domain.usecase;

import com.rpgplace.application.domain.exception.ResourceNotFoundException;
import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.TableUseCasePort;
import com.rpgplace.application.domain.port.in.UserUseCasePort;
import com.rpgplace.application.domain.port.out.TableRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

@Service
@RequiredArgsConstructor
public class TableService implements TableUseCasePort {

    private final TableRepositoryPort tableRepositoryPort;

    private final UserUseCasePort userUseCasePort;

    @Override
    @LogException(message = "Error creating table")
    public TableEntity createTable(TableEntity tableEntity) {

        UserEntity user = userUseCasePort.getUserById(tableEntity.getMaster().getId());

        tableEntity.setMaster(user);

        return tableRepositoryPort.save(tableEntity);
    }

    @Override
    @LogException(message = "Error getting table by id")
    public TableEntity getTableById(UUID id) {
        return tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
    }

    @Override
    @LogException(message = "Error getting all tables")
    public List<TableEntity> getAllTables() {
        return tableRepositoryPort.findAll();
    }

    @Override
    @LogException(message = "Error finding tables by master")
    public List<TableEntity> findTablesByMaster(UUID masterId) {
        return tableRepositoryPort.findByMasterId(masterId);
    }

    @Override
    @LogException(message = "Error finding tables by player")
    public List<TableEntity> findTablesByPlayer(UUID playerId) {
        return tableRepositoryPort.findByCharacters_UserId(playerId);
    }

    @Override
    @LogException(message = "Error finding table by access code")
    public TableEntity findTableByAccessCode(String accessCode) {
        return tableRepositoryPort.findByAccessCode(accessCode)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with access code: " + accessCode));
    }

    @Override
    @LogException(message = "Error updating table")
    public TableEntity updateTable(UUID id, TableEntity tableEntity) {
        TableEntity existingTable = tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));

        existingTable.setTitle(tableEntity.getTitle());
        existingTable.setRulebook(tableEntity.getRulebook());

        return tableRepositoryPort.save(existingTable);
    }

    @Override
    @LogException(message = "Error deleting table")
    public void deleteTable(UUID id) {
        tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        tableRepositoryPort.deleteById(id);
    }
}
