package com.rpgplace.application.domain.usecase;

import com.rpgplace.application.domain.exception.ResourceNotFoundException;
import com.rpgplace.application.domain.model.UserEntity;
import com.rpgplace.application.domain.port.in.UserUseCasePort;
import com.rpgplace.application.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.rpgplace.application.infrastructure.aop.LogException;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCasePort, UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @LogException(message = "Error creating user")
    public UserEntity createUser(UserEntity userEntity) {
        // Logic to hash password will be added in the AuthController/Register method
        return userRepositoryPort.save(userEntity);
    }

    @Override
    @LogException(message = "Error getting user by id")
    public UserEntity getUserById(UUID id) {
        return userRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    @LogException(message = "Error getting all users")
    public List<UserEntity> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    @LogException(message = "Error updating user")
    public UserEntity updateUser(UUID id, UserEntity userEntity) {
        UserEntity existingUser = userRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setName(userEntity.getName());
        existingUser.setEmail(userEntity.getEmail());
        // Password update should have a separate, more secure flow
        return userRepositoryPort.save(existingUser);
    }

    @Override
    @LogException(message = "Error deleting user")
    public void deleteUser(UUID id) {
        if (!userRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepositoryPort.deleteById(id);
    }

    @Override
    @LogException(message = "Error loading user by username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We use email as the username
        return userRepositoryPort.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
