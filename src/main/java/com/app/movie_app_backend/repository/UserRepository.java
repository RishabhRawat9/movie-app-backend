package com.app.movie_app_backend.repository;

import com.app.movie_app_backend.model.UserInfo;

import com.app.movie_app_backend.model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> findByEmail(String email);

    @Query("SELECT u FROM UserInfo u LEFT JOIN FETCH u.lists WHERE u.id = :id")
    Optional<UserInfo> fetchUserWithLists(@Param("id") Long id);
    //ok so by default jpa does lazy fetching when fetching collections and doesn't fetch it unless done in one hibernate session (inside a transaction or explicitly through the jpql query)
    // fetch is of two types eager and lazy , eager for non-collections like Long, INTEGER, Single objs, but for collections it's
    //lazy by default for performance optimizations.


}
