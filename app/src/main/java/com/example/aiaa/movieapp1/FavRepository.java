package com.example.aiaa.movieapp1;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;

import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.Models.Movie;

public class FavRepository {

    private DaoFavourite daoFavourite;
    private LiveData<List<Article>> listLiveData;


    FavRepository(Application application) {

        FavouritDatabase favouritDatabase = FavouritDatabase.getInstance(application);
        daoFavourite = favouritDatabase.daoFavourite();
        listLiveData = daoFavourite.fetchAllArticles();
        Log.d("Aia","insertion done!"+listLiveData.toString()+getMovieList()+listLiveData);

    }

    LiveData<List<Article>> getMovieList() {
        return listLiveData;
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
            Log.d("Aia","insertion done!"+daoFavouriteAsync.fetchAllArticles());

        }
    }
}
