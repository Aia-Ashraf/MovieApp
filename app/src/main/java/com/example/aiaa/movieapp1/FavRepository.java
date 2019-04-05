package com.example.aiaa.movieapp1;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FavRepository {

    private DaoFavourite daoFavourite;
    private LiveData<List<Movie>> movieList;


    FavRepository(Application application) {

        FavouritDatabase favouritDatabase = FavouritDatabase.getInstance(application);
        daoFavourite = favouritDatabase.daoFavourite();
        movieList = daoFavourite.fetchAllMovies();
        Log.d("Aia","insertion done!"+movieList.toString()+getMovieList()+movieList);

    }

    LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public  void insert(Movie movie) {
        new insertAsyncTask(daoFavourite).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        public DaoFavourite daoFavouriteAsync;

        public insertAsyncTask(DaoFavourite dao) {

            daoFavouriteAsync = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movie) {
        //    daoFavouriteAsync.insertFave(movie[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Aia","insertion done!"+daoFavouriteAsync.fetchAllMovies());

        }
    }
}
