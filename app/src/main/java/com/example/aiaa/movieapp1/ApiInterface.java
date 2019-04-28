package com.example.aiaa.movieapp1;


import com.example.aiaa.movieapp1.Models.KoraList;
import com.example.aiaa.movieapp1.Models.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v2/top-headlines")
    Call<KoraList> Data(@Query("country") String country, @Query("category") String category, @Query("apiKey") String api_key);

    interface TopRated {
        @GET("3/movie/top_rated")
        Call<MoviesList> getTopRated(@Query("api_key") String api_key);

    }

    interface Movietrailers {
        @GET("3/movie/{movie_id}/videos")
        Call<MovieTrailerList> getMovieTrailers(
                @Path("movie_id") String movie_id, @Query("api_key") String api_key);

    }

    interface MovieReviewAPI {
        @GET("3/movie/{movie_id}/reviews")
        Call<Reviews> getMovieReview(
                @Path("movie_id") String movie_id, @Query("api_key") String api_key);

    }
}
