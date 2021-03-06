package com.example.aiaa.movieapp1;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.analytics.FirebaseAnalytics;

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
    private Parcelable mLayoutManagerSavedState;


    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private GridLayoutManager gridLayoutManager;

    public FavouritDatabase mDB;

    //  public static final int NEW_DETAILS_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("LIST_INSTANCE_STATE", gridLayoutManager.onSaveInstanceState());

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
// Obtain the FirebaseAnalytics instance.

//        scheduleJob(this);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        //creating new job and adding it with dispatcher

        Job myJob = dispatcher.newJobBuilder()
                .setService(ScheduledJobService.class) // the JobService that will be called
                .setTag("my-unique-tag")        // uniquely identifies the job
                .build();

        dispatcher.mustSchedule(myJob);

        recyclerView = findViewById(R.id.recyclerview);

        //    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        int numberOfColumns = 2;
        gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(gridLayoutManager);
        // recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getRetrofitResponse();
        movieAdapter = new MovieAdapter(this, list);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (savedInstanceState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
        } else {
            // initialize the list to a new empty list
            list = new ArrayList<>();
            movieAdapter.setMovieList(list);


            // kick off the data fetching task
        }

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

    public static void scheduleJob(Context context) {
        //creating new firebase job dispatcher


//        Job job = createJob(dispatcher);
//        dispatcher.mustSchedule(job);
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher) {

        Job job = dispatcher.newJobBuilder()
                //persist the task across boots
                .setLifetime(Lifetime.FOREVER)
                //.setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                //call this service when the criteria are met.
                .setService(ScheduledJobService.class)
                //unique id of the task
                .setTag("UniqueTagForYourJob")
                //don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // We are mentioning that the job is periodic.
                .setRecurring(true)
                // Run between 30 - 60 seconds from now.
                .setTrigger(Trigger.executionWindow(30, 60))
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                //Run this job only when the network is available.
                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING)
                .build();
        return job;
    }

    public static Job updateJob(FirebaseJobDispatcher dispatcher) {
        Job newJob = dispatcher.newJobBuilder()
                //update if any task with the given tag exists.
                .setReplaceCurrent(true)
                //Integrate the job you want to start.
                .setService(ScheduledJobService.class)
                .setTag("UniqueTagForYourJob")
                // Run between 30 - 60 seconds from now.
                .setTrigger(Trigger.executionWindow(30, 60))
                .build();
        return newJob;
    }

    public void cancelJob(Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        //Cancel all the jobs for this package
        dispatcher.cancelAll();
        // Cancel the job for this tag
        dispatcher.cancel("UniqueTagForYourJob");

    }

}