package com.example.aiaa.movieapp1;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/popular")
    Call<MoviesList>Data(@Query("api_key")String api_key);
}
