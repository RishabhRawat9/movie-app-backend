package com.app.movie_app_backend.service;


import com.app.movie_app_backend.model.*;
import com.app.movie_app_backend.repository.ListRepo;
import com.app.movie_app_backend.repository.MovieRepo;
import com.app.movie_app_backend.repository.UserRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final OkHttpClient client = new OkHttpClient();

    private final ListRepo listRepo;
    private final UserRepository userRepo;
    private final MovieRepo movieRepo;


    public MovieService(ListRepo listRepo,UserRepository userRepo,MovieRepo movieRepo){
        this.listRepo = listRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;

    }

    @Transactional
    public List<ListDto> getLists(Long id){
        UserInfo user = userRepo.fetchUserWithLists(id).orElseThrow();
        List<UserList>lists = user.getLists();
        List<ListDto> res = lists.stream().map(obj -> ListDto.builder().name(obj.getName())
                .description(obj.getDescription()).build()).toList();
        return res;
    }




    public String addList(ListDto dto){
        Optional<UserInfo> user = userRepo.findById(dto.getId());
        if(user.isPresent()){
            UserList list = UserList.builder()
                    .userInfo(user.get())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .build();

            listRepo.save(list);
            return "list created";
        }
        else{
            throw new RuntimeException("invalid user");
        }

    }
    @Transactional
    public String saveMovieToLists(Long userId, AddMovieDto dto) {
        // Get the user
        Optional<UserInfo> user = userRepo.findById(userId);
        if (user.isPresent()) {
            for (String listName : dto.getLists()) {
                Optional<UserList> list = listRepo.findByName(listName);
                if (list.isEmpty()) {
                    throw new RuntimeException("Invalid list: " + listName);
                }
                Movie movie = Movie.builder().id(dto.getMovieId()).build();
                movieRepo.save(movie);
                UserList userList = list.get();
                userList.getMovies().add(movie);
                listRepo.save(userList);
                return "movie saved";
            }
        }
            throw new RuntimeException("Invalid user");
    }


    public String searchMovie(String name, int page) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query="+ name +"&include_adult=false&language=en-US&page="+page)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTAzOGIzNmM2ODZhZjhhMTlhYjYyM2NkZmZiYzg0ZCIsIm5iZiI6MTc0MzUyNjczNC4zNiwic3ViIjoiNjdlYzFiNGUwYmQ2NjM0Yjg1MmZiZTZhIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.TKRmFslgHpThFJAx_u8Am6DtVdd6eo8Z9FF93-qGHeg")
                .build();


        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Empty response body");
            }
        }
    }
    public String trendingMovies(int page) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page="+page+"&sort_by=popularity.desc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTAzOGIzNmM2ODZhZjhhMTlhYjYyM2NkZmZiYzg0ZCIsIm5iZiI6MTc0MzUyNjczNC4zNiwic3ViIjoiNjdlYzFiNGUwYmQ2NjM0Yjg1MmZiZTZhIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.TKRmFslgHpThFJAx_u8Am6DtVdd6eo8Z9FF93-qGHeg")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Empty response body");
            }
        }
    }

}
