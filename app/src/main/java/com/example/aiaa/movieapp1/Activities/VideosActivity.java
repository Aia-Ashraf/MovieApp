package com.example.aiaa.movieapp1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.aiaa.movieapp1.ApiInterface;
import com.example.aiaa.movieapp1.MovieTrailer;
import com.example.aiaa.movieapp1.MovieTrailerList;
import com.example.aiaa.movieapp1.R;
import com.example.aiaa.movieapp1.TrailersMovieAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.aiaa.movieapp1.Activities.DeatailsActivity.BASE_URL;

public class VideosActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TrailersMovieAdapter trailersMovieAdapter;
    public List<MovieTrailer> movieTrailersList;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);


        recyclerView = findViewById(R.id.video_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideosActivity.this));
        recyclerView.setHasFixedSize(true);
    }
    public void getTrailers() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        retrofit.create(ApiInterface.Movietrailers.class).getMovieTrailers("iddd", getString(R.string.API_key)).enqueue(new Callback<MovieTrailerList>() {
            @Override
            public void onResponse(Call<MovieTrailerList> call, Response<MovieTrailerList> response) {
                try {
                    if (response.code() == 200) {
                        MovieTrailerList movieResult = response.body();
                        movieTrailersList = movieResult.getMovieTrailers();
                        trailersMovieAdapter.setMovieTrailersListList(movieTrailersList);

                    } else {
                        Toast.makeText(VideosActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(VideosActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieTrailerList> call, Throwable t) {
                Toast.makeText(VideosActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void main(String[] args) throws IOException {

        YouTube youtube = getYouTubeService();
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails,statistics");
            parameters.put("id", "Ks-_Mh1QhMc");

            YouTube.Videos.List videosListByIdRequest = youtube.videos().list(parameters.get("part").toString());
            if (parameters.containsKey("id") && parameters.get("id") != "") {
                videosListByIdRequest.setId(parameters.get("id").toString());
            }

            VideoListResponse response = videosListByIdRequest.execute();
            System.out.println(response);
        }
    }
}
