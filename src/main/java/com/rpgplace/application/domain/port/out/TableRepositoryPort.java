package com.rpgplace.application.domain.port.out;

import com.rpgplace.application.domain.model.TableEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

public interface TableRepositoryPort {

    @LogException(message = "Error saving table")
    TableEntity save(TableEntity tableEntity);

    @LogException(message = "Error finding table by id")
    Optional<TableEntity> findById(UUID id);

    @LogException(message = "Error finding all tables")
    List<TableEntity> findAll();

    @LogException(message = "Error finding tables by master id")
    List<TableEntity> findByMasterId(UUID masterId);

    @LogException(message = "Error finding tables by character user id")
    List<TableEntity> findByCharacters_UserId(UUID playerId);

    @LogException(message = "Error finding table by access code")
    Optional<TableEntity> findByAccessCode(String accessCode);

    @LogException(message = "Error deleting table by id")
    void deleteById(UUID id);
}
