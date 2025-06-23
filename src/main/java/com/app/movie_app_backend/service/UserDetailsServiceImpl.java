
package com.app.movie_app_backend.service;

import com.app.movie_app_backend.model.UserInfo;
import com.app.movie_app_backend.repository.UserRepository;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = repository.findByEmail(username).orElseThrow();
        return org.springframework.security.core.userdetails.User.builder() // returning a user obj which does implement
                // the userdetails but we only set the name
                // and password.
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}