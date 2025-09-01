package com.rpgplace.application.infrastructure.adapter.persistence.repository;

import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.domain.port.out.TableRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, UUID>, TableRepositoryPort {

    @Override
    @Query("SELECT DISTINCT t FROM TableEntity t JOIN t.characters c WHERE c.user.id = :playerId")
    List<TableEntity> findByCharacters_UserId(@Param("playerId") UUID playerId);

    Optional<TableEntity> findByAccessCode(String accessCode);
}
