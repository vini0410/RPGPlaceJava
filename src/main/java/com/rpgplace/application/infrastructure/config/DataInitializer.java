package com.rpgplace.application.infrastructure.config;

import com.rpgplace.application.domain.model.CharacterEntity;
import com.rpgplace.application.domain.model.TableEntity;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.CharacterUseCasePort;
import com.rpgplace.application.domain.port.in.TableUseCasePort;
import com.rpgplace.application.domain.port.in.UserUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserUseCasePort userUseCasePort;
    private final TableUseCasePort tableUseCasePort;
    private final CharacterUseCasePort characterUseCasePort;

    @Override
    public void run(String... args) throws Exception {
        var u1 = new UserEntity();
        var u2 = new UserEntity();
        var t1 = new TableEntity();
        var t2 = new TableEntity();
        var c1 = new CharacterEntity();
        var c2 = new CharacterEntity();

        u1.setName("vini");
        u1.setEmail("vini@email.com");
        u1.setPassword(passwordEncoder.encode("12345678"));

        u2.setName("teste");
        u2.setEmail("teste@email.com");
        u2.setPassword(passwordEncoder.encode("12345678"));

        u1 = userUseCasePort.createUser(u1);
        u2 = userUseCasePort.createUser(u2);

        t1.setTitle("mesa teste 1");
        t1.setAccessCode(UUID.randomUUID().toString());
        t1.setCreatedAt(OffsetDateTime.now());
        t1.setRulebook("D&D");
        t1.setMaster(u1);

        t2.setTitle("mesa teste 2");
        t2.setAccessCode(UUID.randomUUID().toString());
        t2.setCreatedAt(OffsetDateTime.now());
        t2.setRulebook("D&D");
        t2.setMaster(u2);

        t1 = tableUseCasePort.createTable(t1);
        t2 = tableUseCasePort.createTable(t2);

        c1.setName("mago");
        c1.setHealth(100);
        c1.setMana(100);
        c1.setStrength(10);
        c1.setAgility(10);
        c1.setIntelligence(10);
        c1.setTable(t1);
        c1.setUser(u2);

        c2.setName("cavaleiro");
        c2.setHealth(100);
        c2.setMana(100);
        c2.setStrength(10);
        c2.setAgility(10);
        c2.setIntelligence(10);
        c2.setTable(t2);
        c2.setUser(u1);

        characterUseCasePort.createCharacter(c1);
        characterUseCasePort.createCharacter(c2);

    }
}
