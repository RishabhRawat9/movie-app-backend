package com.app.movie_app_backend.controller;

import com.app.movie_app_backend.model.ListDto;
import com.app.movie_app_backend.service.MovieService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;

@RestController

@RequestMapping("/api")
@CrossOrigin
public class Controller {
    @Autowired
    public MovieService movieService;

    @GetMapping("/popular/{page}")
    public String trendingMovies(@PathVariable int page) throws IOException {
        return movieService.trendingMovies(page);
    }
    @GetMapping("/search/{name}/{page}")
    public String searchMovie(@PathVariable String name , @PathVariable int page) throws IOException {
        return movieService.searchMovie(name, page);
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
