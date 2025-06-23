package com.app.movie_app_backend.repository;

import com.app.movie_app_backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Long> {
}
