package com.rpgplace.application.infrastructure.config;

import com.rpgplace.application.domain.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("webSecurity")
public class WebSecurity {

    public boolean checkUserId(Authentication authentication, UUID id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return currentUser.getId().equals(id);
    }
}
