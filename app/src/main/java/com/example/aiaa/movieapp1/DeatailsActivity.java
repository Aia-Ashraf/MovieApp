package com.example.aiaa.movieapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aiaa.movieapp1.Models.Movie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class DeatailsActivity extends AppCompatActivity {

    String firstURLPart = "http://image.tmdb.org/t/p/w185";
    ImageView imageView;
    TextView title;
    Movie movie;
    TextView releaseDate;
    TextView vote;
    TextView details;

    private Retrofit retrofit;
    public static String BASE_URL = "https://api.themoviedb.org/";
    public String movieID;
    private TrailersMovieAdapter trailersAdapter;
    public List<MovieTrailer> movieTrailersList;
    public List<ReviewsModel> reviewsModelList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewReviews;
    public ToggleButton toggleButton;
    private FavouritDatabase mDB;
    ReviewsAdapter reviewsAdapter;
    private favViewModel favviewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatails);

        favviewmodel = ViewModelProviders.of(this).get(favViewModel.class);
        favviewmodel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.e("Aia", "favRetriveDetailsActivity" + movie);

            }
        });


        imageView = findViewById(R.id.image_iv);
        title = findViewById(R.id.tv_title);
        releaseDate = findViewById(R.id.tv_releaseDate);
        vote = findViewById(R.id.tv_vote);
        details = findViewById(R.id.tv_details);
        toggleButton = findViewById(R.id.myToggleButton);
        mDB = FavouritDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        movie = intent.getExtras().getParcelable("parcelable_extra");
        String PhotoPath = movie.getPosterPath();
        String MovieTitle = movie.getTitle();
        String MovieReleseData = movie.getReleaseDate();
        Double voteMoview = movie.getVoteAverage();
        String plot = movie.getOverview();
        movieID = movie.id.toString();


        recyclerView = findViewById(R.id.vv);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);

        recyclerViewReviews = findViewById(R.id.RV_Reviews);

        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(DeatailsActivity.this));
        recyclerViewReviews.setHasFixedSize(true);


        if (mDB.daoFavourite().fetchOneMoviesbyMovieId(MovieTitle) == null) {

            mDB.daoFavourite().insertOnlySingleMovie(movie);
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.disable));
            Log.e("Fav", "inserted" + mDB);

            // favviewmodel.insert(movie);

        } else {
            mDB.daoFavourite().deleteMovie(movie);
            toggleButton.setChecked(true);
            Log.e("Fav", "deteted" + mDB);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.enable));
            Log.e("Fav", "notExist");


        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.disable));

                } else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.enable));
                FirebaseApp.initializeApp(DeatailsActivity.this);

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("DeatailsActivity", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log and toast
                                Log.d("DeatailsActivityToken", token);
                                Toast.makeText(DeatailsActivity.this, token, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        toggleButton.setText(null);
        toggleButton.setTextOn(null);
        toggleButton.setTextOff(null);


        getTrailers();
        RetrofitGetReviews();
        Log.e("movieID", movieID + "");
        trailersAdapter = new TrailersMovieAdapter(this, movieTrailersList);
        recyclerView.setAdapter(trailersAdapter);
        movieTrailersList = new ArrayList<>();
        trailersAdapter.setMovieTrailersListList(movieTrailersList);


        reviewsAdapter = new ReviewsAdapter(this, reviewsModelList);
        recyclerViewReviews.setAdapter(reviewsAdapter);
        reviewsModelList = new ArrayList<>();
        reviewsAdapter.setMovieReviewList(reviewsModelList);


        Picasso.get().load(firstURLPart + PhotoPath)
                .resize(200, 200)
                .centerCrop()
                .placeholder(R.drawable.scope_placeholder)
                .into(imageView);

        title.setText(MovieTitle);
        releaseDate.setText("Release Data : " + MovieReleseData);
        vote.setText(String.valueOf(voteMoview));
        details.setText(plot);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.Top_Rated).setVisible(false);
        menu.findItem(R.id.Popularty).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.back) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTrailers() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        retrofit.create(ApiInterface.Movietrailers.class).getMovieTrailers(movieID, getString(R.string.API_key)).enqueue(new Callback<MovieTrailerList>() {
            @Override
            public void onResponse(Call<MovieTrailerList> call, Response<MovieTrailerList> response) {
                try {
                    if (response.code() == 200) {
                        MovieTrailerList movieResult = response.body();
                        movieTrailersList = movieResult.getMovieTrailers();
                        trailersAdapter.setMovieTrailersListList(movieTrailersList);

                    } else {
                        Toast.makeText(DeatailsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DeatailsActivity.this, R.string.error, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<MovieTrailerList> call, Throwable t) {
                Toast.makeText(DeatailsActivity.this, R.string.error, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void RetrofitGetReviews() {

        retrofit.create(ApiInterface.MovieReviewAPI.class).getMovieReview(movieID, getString(R.string.API_key)).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.code() == 200) {


                    Reviews reviews = response.body();
                    reviewsModelList = reviews.getResults();
                    reviewsAdapter.setMovieReviewList(reviewsModelList);


                    //  reviewsModelList = response.body().getResults();
                    //  String auther = reviewsModelList.getString("NeededString");


                    Log.e("Aia", reviews.toString());
                } else {
                    Toast.makeText(DeatailsActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

}