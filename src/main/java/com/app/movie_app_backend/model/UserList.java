package com.app.movie_app_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity //for each list we need an id,createdAt, moviesStored(list of movies),userId(fk), title
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInfo userId;

    @ManyToMany()
    @JoinTable(
            name = "userlist_movie",
            joinColumns = @JoinColumn(name = "userlist_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;


}
