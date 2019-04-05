package com.example.aiaa.movieapp1;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {


    private Retrofit retrofit;
    public static String BASE_URL = "https://api.themoviedb.org/";

    private List<Movie> list;

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;


    public FavouritDatabase mDB;

    //  public static final int NEW_DETAILS_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview);

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

        mDB = FavouritDatabase.getInstance(getApplicationContext());

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

                        } else {

                            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<MoviesList> call, Throwable t) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG);
                }
            });
        }
    }

    public void getTopRatedRetrofitResponse() {

        retrofit.create(ApiInterface.TopRated.class).getTopRated(getString(R.string.API_key)).enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                try {
                    if (response.code() == 200) {
                        list = response.body().getMovies();
                        movieAdapter.setMovieList(list);

                    } else {
                        Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {

                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.back).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Top_Rated) {
            //   Collections.sort(list,Movie.Sort_BY_Rate);
            setTitle(R.string.Top_Rated);
            getTopRatedRetrofitResponse();
            movieAdapter = new MovieAdapter(this, list);
            recyclerView.setAdapter(movieAdapter);
            list = new ArrayList<>();
            movieAdapter.setMovieList(list);

        } else if (item.getItemId() == R.id.Popularty) {
            setTitle(R.string.app_name);
            getRetrofitResponse();
            movieAdapter = new MovieAdapter(this, list);
            recyclerView.setAdapter(movieAdapter);
            list = new ArrayList();
            movieAdapter.setMovieList(list);

        }
        return super.onOptionsItemSelected(item);
    }


}