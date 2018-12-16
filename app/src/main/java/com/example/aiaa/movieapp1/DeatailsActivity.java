package com.example.aiaa.movieapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DeatailsActivity extends AppCompatActivity {

    String firstURLPart = "http://image.tmdb.org/t/p/w185";
    ImageView imageView;
    TextView title;
    Movie movie;
    TextView releaseDate;
    TextView vote;
    TextView details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatails);

        imageView = (ImageView) findViewById(R.id.image_iv);
        title = (TextView) findViewById(R.id.tv_title);
        releaseDate =(TextView)findViewById(R.id.tv_releaseDate);
        vote=(TextView)findViewById(R.id.tv_vote);
        details=(TextView)findViewById(R.id.tv_details);


        Intent intent = getIntent();

        movie = intent.getExtras().getParcelable("parcelable_extra");
        String PhotoPath = movie.getPosterPath();
        String MovieTitle = movie.getTitle();
        String MovieReleseData = movie.getReleaseDate();
        Double voteMoview = movie.getVoteAverage();
        String plot = movie.getOverview();


        Picasso.get().load(firstURLPart + PhotoPath)
                .resize(250, 350)
                .centerCrop()
                .placeholder(R.drawable.scope_placeholder)
                .into(imageView);

        title.setText(MovieTitle);
        releaseDate.setText(MovieReleseData);
        vote.setText(String.valueOf(voteMoview));
        details.setText(plot);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
}

