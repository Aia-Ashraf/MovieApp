package com.example.aiaa.movieapp1;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class favViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movie ;
    DeatailsActivity deatailsActivity;

    private FavRepository favRepository ;



    public favViewModel(@NonNull Application application) {
        super(application);
        favRepository = new FavRepository(application);
        movie =favRepository.getMovieList();
        Log.e("Aia","favRetriveViewModel"+movie);
        }

public LiveData<List<Movie>>getMovie(){
        return movie;

}
    public void insert(Movie movie) { favRepository.insert(movie);


    }

}
