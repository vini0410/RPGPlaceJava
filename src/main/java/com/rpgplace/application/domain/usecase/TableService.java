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

@Service
@RequiredArgsConstructor
public class TableService implements TableUseCasePort {

    private final TableRepositoryPort tableRepositoryPort;

    private final UserUseCasePort userUseCasePort;

    @Override
    public TableEntity createTable(TableEntity tableEntity) {

        UserEntity user = userUseCasePort.getUserById(tableEntity.getMaster().getId());

        tableEntity.setMaster(user);

        return tableRepositoryPort.save(tableEntity);
    }

    @Override
    public TableEntity getTableById(UUID id) {
        return tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
    }

    @Override
    public List<TableEntity> getAllTables() {
        return tableRepositoryPort.findAll();
    }

    @Override
    public List<TableEntity> findTablesByMaster(UUID masterId) {
        return tableRepositoryPort.findByMasterId(masterId);
    }

    @Override
    public List<TableEntity> findTablesByPlayer(UUID playerId) {
        return tableRepositoryPort.findByCharacters_UserId(playerId);
    }

    @Override
    public TableEntity findTableByAccessCode(String accessCode) {
        return tableRepositoryPort.findByAccessCode(accessCode)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with access code: " + accessCode));
    }

    @Override
    public TableEntity updateTable(UUID id, TableEntity tableEntity) {
        TableEntity existingTable = tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));

        existingTable.setTitle(tableEntity.getTitle());
        existingTable.setRulebook(tableEntity.getRulebook());

        return tableRepositoryPort.save(existingTable);
    }

    @Override
    public void deleteTable(UUID id) {
        tableRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        tableRepositoryPort.deleteById(id);
    }
}
