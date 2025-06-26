package com.app.movie_app_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieDto {

    public Long userId;
    public Long movieId;
    public List<String>lists;

}
