package com.example.aiaa.movieapp1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    public String imgURL;


    private Retrofit retrofit;
    public static String BASE_URL = "https://api.themoviedb.org/";

    private List<Movie> list;

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

    //    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
       // recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getRetrofitResponse();
        movieAdapter = new MovieAdapter(this, list);
        recyclerView.setAdapter(movieAdapter);

        list = new ArrayList<>();
        movieAdapter.setMovieList(list);

    }

    public void getRetrofitResponse() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            retrofit.create(ApiInterface.class).Data(getString(R.string.API_key)).enqueue(new Callback<MoviesList>() {
                @Override
                public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                    try {
                        if (response.code() == 200) {

                            list = response.body().getMovies();
                            movieAdapter.setMovieList(list);
                        }else {

                            Toast.makeText(MainActivity.this, R.string.error,Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<MoviesList> call, Throwable t) {
                    Toast.makeText(MainActivity.this,R.string.error,Toast.LENGTH_LONG);
                }
            });
        }
    }
}