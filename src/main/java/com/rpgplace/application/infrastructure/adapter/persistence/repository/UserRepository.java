package com.rpgplace.application.infrastructure.adapter.persistence.repository;

import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.out.UserRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, UserRepositoryPort {
}
