package com.rpgplace.application.domain.port.in;

import com.rpgplace.application.domain.model.TableEntity;

import java.util.List;
import java.util.UUID;

public interface TableUseCasePort {

    TableEntity createTable(TableEntity tableEntity);

    TableEntity getTableById(UUID id);

    List<TableEntity> getAllTables();

    List<TableEntity> findTablesByMaster(UUID masterId);

    List<TableEntity> findTablesByPlayer(UUID playerId);

    TableEntity findTableByAccessCode(String accessCode);

    TableEntity updateTable(UUID id, TableEntity tableEntity);

    void deleteTable(UUID id);
}
