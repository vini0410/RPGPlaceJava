package com.rpgplace.application.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.UserResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(userEntity);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(userResponseDTO));
    }
}
