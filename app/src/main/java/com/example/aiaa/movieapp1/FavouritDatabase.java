package com.example.aiaa.movieapp1;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.Models.Movie;


@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class FavouritDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "articles_db";
    private static final Object Lock = new Object();

    private static FavouritDatabase favouritDatabase;

    public static FavouritDatabase getInstance(Context context) {
        if (favouritDatabase == null) {
            synchronized (Lock) {
                favouritDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        FavouritDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .addCallback(roomCallBack)
                        //     .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return favouritDatabase;

    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(favouritDatabase).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private  DaoFavourite mDao;

        PopulateDbAsync(FavouritDatabase db) {
            mDao = db.daoFavourite();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteMovie();
//            Movie movie = new Movie("Hello");
//            mDao.f(word);
//            word = new Word("World");
//            mDao.insert(word);

            Log.e("Aia","Databaseeeeeeeeeeeeeeeeeeeeeeeeeee");
            return null;
        }
    }


    public abstract DaoFavourite daoFavourite();
}
