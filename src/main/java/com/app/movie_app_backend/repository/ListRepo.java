package com.app.movie_app_backend.repository;

import com.app.movie_app_backend.model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListRepo extends JpaRepository<UserList, Long> {
    Optional<UserList> findByName(String name);
}
