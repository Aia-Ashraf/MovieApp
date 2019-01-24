package com.example.aiaa.movieapp1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> {
    private List<Movie> modelList;
    String firstURLPart = "http://image.tmdb.org/t/p/w185";
    private Context context;

    public Movie mPosition;

    public MainActivity mainActivity;
    Movie movie;


    public MovieAdapter(Context context, List<Movie> modelList) {
        this.context = context;
        this.modelList = modelList;
        mPosition = null;
    }

    @Override
    public movieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_img;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new movieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final movieViewHolder movieViewHolder, int position) {
        final Movie mPosition = modelList.get(position);
        Double voteMoview = mPosition.getVoteAverage();

        if (mPosition.getPosterPath() != null) {
            Picasso.get().load(firstURLPart + mPosition.getPosterPath())

                    .resize(700, 1165)
                    .centerCrop()
                    .placeholder(R.drawable.scope_placeholder)
                    .into(movieViewHolder.img);
            movieViewHolder.titleMain.setText(mPosition.getTitle());
            movieViewHolder.rate.setText(String.valueOf(voteMoview));

        }
        movieViewHolder.img.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (mPosition.getPosterPath() != null) {
                    Intent intent = new Intent(context, DeatailsActivity.class)
                            .putExtra("parcelable_extra", (Parcelable) mPosition);
                    context.startActivity(intent);

                }

            }
        });

        Log.e("p", position + "");



    }

    @Override
    public int getItemCount() {
        if (null == modelList) return 0;
        return modelList.size();

    }

    public void setMovieList(List<Movie> list) {
        modelList = list;
        notifyDataSetChanged();
    }


    static class movieViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView titleMain;
        public TextView rate;


        public movieViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_item);
            titleMain = (TextView) itemView.findViewById(R.id.tv_main_title);
            rate = (TextView) itemView.findViewById(R.id.tv_main_rate);

        }
    }


}

