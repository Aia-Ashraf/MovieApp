package com.example.aiaa.movieapp1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aiaa.movieapp1.ApiInterface;
import com.example.aiaa.movieapp1.FavouritDatabase;
import com.example.aiaa.movieapp1.Models.Article;
import com.example.aiaa.movieapp1.MovieTrailer;
import com.example.aiaa.movieapp1.MovieTrailerList;
import com.example.aiaa.movieapp1.R;
import com.example.aiaa.movieapp1.Reviews;
import com.example.aiaa.movieapp1.ReviewsAdapter;
import com.example.aiaa.movieapp1.ReviewsModel;
import com.example.aiaa.movieapp1.TrailersMovieAdapter;
import com.example.aiaa.movieapp1.favViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
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
    TextView title,releaseDate,content,description,URL;
    Article article;


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
        /*favviewmodel.getMovie().observe(DeatailsActivity, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.e("Aia", "favRetriveDetailsActivity" + movie);

            }
        }
        );*/

        imageView = findViewById(R.id.image_iv);
        title = findViewById(R.id.tv_title);
        releaseDate = findViewById(R.id.tv_releaseDate);
        URL = findViewById(R.id.tv_url);
        description = findViewById(R.id.tv_details);
//        toggleButton = findViewById(R.id.myToggleButton);
        mDB = FavouritDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        article = intent.getExtras().getParcelable("parcelable_extra_details");
//        String PhotoPath = article.getPosterPath();
        String articleTitle = article.getTitle();
        String articlePublishedAt = article.getPublishedAt();
        String articleContent = article.getContent();
        String articleDescription = article.getDescription();
        String url=article.getUrl();
        movieID = article.getAuthor();

//        recyclerView = findViewById(R.id.vv);
//        int numberOfColumns = 1;
//        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//        recyclerView.setHasFixedSize(true);
//        recyclerViewReviews = findViewById(R.id.RV_Reviews);
//        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(DeatailsActivity.this));
//        recyclerViewReviews.setHasFixedSize(true);


     /*   if (mDB.daoFavourite().fetchOneMoviesbyMovieId(MovieTitle) == null) {
>>>>>>> 2bb08eaa28d3ff9ffe2a18b25a3e088e005094c8

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
*/
      /*  toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
*/
        getTrailers();
//        RetrofitGetReviews();
        Log.e("movieID", movieID + "");
//        trailersAdapter = new TrailersMovieAdapter(this, movieTrailersList);
//        recyclerView.setAdapter(trailersAdapter);
//        movieTrailersList = new ArrayList<>();
//        trailersAdapter.setMovieTrailersListList(movieTrailersList);
//
//        reviewsAdapter = new ReviewsAdapter(this, reviewsModelList);
//        recyclerViewReviews.setAdapter(reviewsAdapter);
//        reviewsModelList = new ArrayList<>();
//        reviewsAdapter.setMovieReviewList(reviewsModelList);

//        Picasso.get().load(firstURLPart + PhotoPath)

        Picasso.get().load(article.getUrlToImage())
                .resize(800, 600)
                .centerCrop()
                .placeholder(R.drawable.scope_placeholder)
                .into(imageView);

        title.setText(articleTitle);
        releaseDate.setText("Published at : " + articlePublishedAt);
//        content.setText(String.valueOf(articleContent));
        description.setText(articleDescription);
//        URL.setText(url);
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