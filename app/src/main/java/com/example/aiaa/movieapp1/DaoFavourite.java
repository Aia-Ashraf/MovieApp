package com.example.aiaa.movieapp1;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.Models.Movie;

@Dao
public interface DaoFavourite {
    @Insert
            (onConflict = OnConflictStrategy.REPLACE)
    void insertOnlySingleMovie(Article article);

    @Insert
            (onConflict = OnConflictStrategy.REPLACE)
    void insertFave(Article article);

    @Insert
    void insertMultipleMovies(List<Article> articleList);

//    @Query("SELECT * FROM ArticleTable WHERE title = :title")
//    Movie fetchOneMoviesbyMovieId(String title);

    @Query("SELECT * FROM ArticleTable ")
    LiveData<List<Article>> fetchAllArticles();

    @Update
            (onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Article article);

    @Delete
        // ("DELETE FROM Movie")
    void deleteMovie(Article article);

//    @Query("SELECT COUNT(ID) FROM ArticleTable")
//    int getCount();

    @Insert
    void insert(Article article);

}
