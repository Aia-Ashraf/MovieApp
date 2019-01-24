package com.example.aiaa.movieapp1;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/popular")
    Call<MoviesList> Data(@Query("api_key") String api_key);

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
