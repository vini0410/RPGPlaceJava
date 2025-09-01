package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.TableEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TableRepositoryPort {

    TableEntity save(TableEntity tableEntity);

    Optional<TableEntity> findById(UUID id);

    List<TableEntity> findAll();

    List<TableEntity> findByMasterId(UUID masterId);

    List<TableEntity> findByCharacters_UserId(UUID playerId);

    Optional<TableEntity> findByAccessCode(String accessCode);

    void deleteById(UUID id);
}
