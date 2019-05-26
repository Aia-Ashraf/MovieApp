package com.example.aiaa.movieapp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aiaa.movieapp1.Adapters.Adapter2;
import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.Models.KoraList;
import com.example.aiaa.movieapp1.Models.Movie;
import com.example.aiaa.movieapp1.Models.MoviesList;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends  FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Retrofit retrofit;
    public static String BASE_URL = "https://newsapi.org/";
    private List<Movie> list;
    private List<Article> articles;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewFirst;
    private FirstAdapter firstAdapter;
    private Adapter2 adapter2;
    public FavouritDatabase mDB;
    DrawerNavFragment drawerNavFragment;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

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
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        // recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getRetrofitResponse();
        adapter2 = new Adapter2(this, articles);
        recyclerView.setAdapter(adapter2);
        adapter2.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        list = new ArrayList<>();
        adapter2.setMovieList(articles);


        recyclerView = findViewById(R.id.recyclerviewFirst);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getRetrofitResponse();
        firstAdapter = new FirstAdapter(this, articles);
        recyclerView.setAdapter(firstAdapter);
        firstAdapter.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        list = new ArrayList<>();
        firstAdapter.setMovieList(articles);

        mDB = FavouritDatabase.getInstance(getApplicationContext());

    }

    public void getRetrofitResponse() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            retrofit.create(ApiInterface.class).Data(getString(R.string.country), getString(R.string.category), getString(R.string.API_key)).enqueue(new Callback<KoraList>() {
                @Override
                public void onResponse(Call<KoraList> call, Response<KoraList> response) {
                    try {
                        if (response.code() == 200 || response.isSuccessful() == true) {

                            articles = response.body().getArticles();
                            firstAdapter.setMovieList(articles);
                            adapter2.setMovieList(articles);

                        } else {

                            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<KoraList> call, Throwable t) {
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
                        firstAdapter.setMovieList(articles);

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
            firstAdapter = new FirstAdapter(this, articles);
            recyclerView.setAdapter(firstAdapter);
            list = new ArrayList<>();
            firstAdapter.setMovieList(articles);

        } else if (item.getItemId() == R.id.Popularty) {
            setTitle(R.string.app_name);
            getRetrofitResponse();
            firstAdapter = new FirstAdapter(this, articles);
            recyclerView.setAdapter(firstAdapter);
            list = new ArrayList();
            firstAdapter.setMovieList(articles);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showdrawerNavFragment(){
        drawerNavFragment = new DrawerNavFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, drawerNavFragment);
        fragmentTransaction.commit();
    }
}