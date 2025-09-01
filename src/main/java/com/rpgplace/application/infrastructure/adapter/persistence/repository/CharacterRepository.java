package com.rpgplace.application.infrastructure.adapter.persistence.repository;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.port.out.CharacterRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID>, CharacterRepositoryPort {
    List<CharacterEntity> findByTableId(UUID tableId);

    List<CharacterEntity> findByUserId(UUID userId);
}
