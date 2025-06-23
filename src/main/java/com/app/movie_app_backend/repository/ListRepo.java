package com.app.movie_app_backend.repository;

import com.app.movie_app_backend.model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepo extends JpaRepository<UserList, Long> {

}
