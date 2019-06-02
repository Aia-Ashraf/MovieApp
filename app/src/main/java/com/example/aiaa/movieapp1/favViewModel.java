package com.example.aiaa.movieapp1;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aiaa.movieapp1.Activities.DeatailsActivity;
import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.Models.Movie;

public class favViewModel extends AndroidViewModel {

    private LiveData<List<Article>> listLiveData ;
    DeatailsActivity deatailsActivity;

    private FavRepository favRepository ;



    public favViewModel(@NonNull Application application) {
        super(application);
        favRepository = new FavRepository(application);
        listLiveData =favRepository.getMovieList();
        Log.e("Aia","favRetriveViewModel"+listLiveData);
        }

public LiveData<List<Article>>getArticle(){
        return listLiveData;

}
    public void insert(Movie movie) { favRepository.insert(movie);


    }

}
