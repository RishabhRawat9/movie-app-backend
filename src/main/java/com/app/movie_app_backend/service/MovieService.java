package com.app.movie_app_backend.service;


import com.app.movie_app_backend.model.ListDto;
import com.app.movie_app_backend.model.UserInfo;
import com.app.movie_app_backend.model.UserList;
import com.app.movie_app_backend.repository.ListRepo;
import com.app.movie_app_backend.repository.UserRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class MovieService {
    private final OkHttpClient client = new OkHttpClient();

    private final ListRepo listRepo;
    private final UserRepository userRepo;


    public MovieService(ListRepo listRepo,UserRepository userRepo){
        this.listRepo = listRepo;
        this.userRepo = userRepo;

    }


    public String addList(ListDto dto){
        Optional<UserInfo> user = userRepo.findById(dto.getId());
        if(user.isPresent()){
            UserList list = UserList.builder()
                    .userId(user.get())
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
