package com.example.aiaa.movieapp1;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiaa.movieapp1.Models.Movie;

@Dao
public interface DaoFavourite {
    @Insert
            (onConflict = OnConflictStrategy.REPLACE)
    void insertOnlySingleMovie(Movie movie);

    @Insert
            (onConflict = OnConflictStrategy.REPLACE)
    void insertFave(Movie movie);

    @Insert
    void insertMultipleMovies(List<Movie> moviesList);

    @Query("SELECT * FROM Movie WHERE title = :title")
    Movie fetchOneMoviesbyMovieId(String title);

    @Query("SELECT * FROM Movie ")
    LiveData<List<Movie>> fetchAllMovies();

    @Update
            (onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
        // ("DELETE FROM Movie")
    void deleteMovie(Movie movie);

    @Query("SELECT COUNT(ID) FROM Movie")
    int getCount();

    @Insert
    void insert(Movie movie);

}
