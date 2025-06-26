package com.app.movie_app_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity //for each list we need an id,createdAt, moviesStored(list of movies),userId(fk), title
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    @Id
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")//is col ka name user_id hoga. & userlist table side pe hi show hoga ye.
    private UserInfo userInfo;


    @ManyToMany()
    @JoinTable(
            name = "userlist_movie",
            joinColumns = @JoinColumn(name = "userlist_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies =new ArrayList<>();


}
