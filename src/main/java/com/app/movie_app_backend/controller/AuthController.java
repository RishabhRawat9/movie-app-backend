package com.app.movie_app_backend.controller;

import com.app.movie_app_backend.model.AuthResponse;
import com.app.movie_app_backend.model.LoginRequest;
import com.app.movie_app_backend.model.SignupRequestDTO;
import com.app.movie_app_backend.repository.UserRepository;
import com.app.movie_app_backend.service.JwtService;
import com.app.movie_app_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDTO requestDto) {
        System.out.println("got request");
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        try{
            AuthResponse response = userService.login(request);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex, HttpStatusCode.valueOf(400));
        }
    }

}
