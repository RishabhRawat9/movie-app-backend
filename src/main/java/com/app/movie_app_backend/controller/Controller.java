package com.app.movie_app_backend.controller;

import com.app.movie_app_backend.model.AddMovieDto;
import com.app.movie_app_backend.model.ListDto;
import com.app.movie_app_backend.model.UserInfo;
import com.app.movie_app_backend.model.UserList;
import com.app.movie_app_backend.repository.ListRepo;
import com.app.movie_app_backend.repository.UserRepository;
import com.app.movie_app_backend.service.MovieService;
import com.mysql.cj.x.protobuf.Mysqlx;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    public MovieService movieService;

    private final UserRepository userRepo;
    private final ListRepo listRepo;

    public Controller(UserRepository userRepo, ListRepo listRepo){
        this.userRepo = userRepo;
        this.listRepo = listRepo;

    }




    @PostMapping("/list/{userId}/add")
    public ResponseEntity<?> saveMovieToLists(@PathVariable Long userId, @RequestBody AddMovieDto dto){
        System.out.println("got called");
        try {
            return new ResponseEntity<>(movieService.saveMovieToLists(userId, dto), HttpStatusCode.valueOf(200));
        }
        catch(Exception ex){
            return new ResponseEntity<>("failed", HttpStatusCode.valueOf(400));
        }
    }


    @GetMapping("/popular/{page}")
    public String trendingMovies(@PathVariable int page) throws IOException {
        return movieService.trendingMovies(page);
    }
    @GetMapping("/search/{name}/{page}")
    public String searchMovie(@PathVariable String name , @PathVariable int page) throws IOException {
        return movieService.searchMovie(name, page);
    }
    @GetMapping("/list/{id}")
    public List<ListDto> getLists(@PathVariable Long id) {
        return movieService.getLists(id);
    }


    @PostMapping("/list/add-new")
    public ResponseEntity<?> addList(@RequestBody ListDto dto){
       try{
           return new ResponseEntity<>(movieService.addList(dto), HttpStatusCode.valueOf(200));
       }
       catch(Exception ex){
           return new ResponseEntity<>(ex, HttpStatusCode.valueOf(400));
       }
    }
}
