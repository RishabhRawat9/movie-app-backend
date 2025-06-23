package com.app.movie_app_backend.service;

import java.util.Optional;

import com.app.movie_app_backend.model.AuthResponse;
import com.app.movie_app_backend.model.LoginRequest;
import com.app.movie_app_backend.model.SignupRequestDTO;
import com.app.movie_app_backend.model.UserInfo;
import com.app.movie_app_backend.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.movie_app_backend.service.JwtService;

@Service

public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;

    }

    public AuthResponse signup(SignupRequestDTO request) {
        String email = request.email();
        Optional<UserInfo> existingUser = repository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateKeyException(String.format("User with the email address '%s' already exists.", email));
        }
        String hashedPassword = passwordEncoder.encode(request.password());

        UserInfo user = UserInfo.builder()
                .username(request.name())
                .email(request.email())
                .password(hashedPassword).build();
        repository.save(user);
        Optional<UserInfo> userInfo = repository.findByEmail(request.email());
        AuthResponse authResponse = AuthResponse.builder().id(user.getId())
                .message("user created").build();

        return authResponse;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<UserInfo> userInfo = repository.findByEmail(request.email());
        if (userInfo.isPresent() && passwordEncoder.matches(request.password(), userInfo.get().getPassword())) {

            AuthResponse authResponse = AuthResponse.builder().id(userInfo.get().getId())
                    .message("user logged in").build();
            return authResponse;
        } else {
            System.out.println("user not found");
            throw new RuntimeException("user not found");
        }
    }

}