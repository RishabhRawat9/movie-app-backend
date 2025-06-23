package com.app.movie_app_backend.repository;

import com.app.movie_app_backend.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmail(String email);

}
