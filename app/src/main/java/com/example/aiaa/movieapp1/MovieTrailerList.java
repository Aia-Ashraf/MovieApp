package com.example.aiaa.movieapp1;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieTrailerList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> MovieTrailers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return MovieTrailers;
    }

    public void setMovieTrailers(List<MovieTrailer> MovieTrailers) {
        this.MovieTrailers = MovieTrailers;
    }
}
