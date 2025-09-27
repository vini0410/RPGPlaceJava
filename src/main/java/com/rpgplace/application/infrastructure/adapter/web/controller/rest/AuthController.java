package com.rpgplace.application.infrastructure.adapter.web.controller.rest;

import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.UserUseCasePort;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.LoginRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.request.RegisterRequestDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.LoginResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.dto.response.UserResponseDTO;
import com.rpgplace.application.infrastructure.adapter.web.mapper.UserMapper;
import com.rpgplace.application.infrastructure.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserUseCasePort userUseCasePort;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UserEntity userEntity = userMapper.toEntity(registerRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        UserEntity createdUser = userUseCasePort.createUser(userEntity);

        // Note: In a real-world scenario, you might want to automatically log the user in here
        // or simply return a success message.
        return new ResponseEntity<>(userMapper.toResponseDTO(createdUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = (UserEntity) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        return new ResponseEntity<>(new LoginResponseDTO(token, userMapper.toResponseDTO(user)), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(@AuthenticationPrincipal UserEntity user) {
        // The 'user' object is automatically injected by Spring Security
        // It contains the details of the currently authenticated user.
        return new ResponseEntity<>(userMapper.toResponseDTO(user), HttpStatus.OK);
    }
}
