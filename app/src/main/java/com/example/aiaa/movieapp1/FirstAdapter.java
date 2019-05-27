package com.example.aiaa.movieapp1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.aiaa.movieapp1.Activities.DeatailsActivity;
import com.example.aiaa.movieapp1.Models.Article;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.movieViewHolder> {
    private List<Article> articleList;
    String firstURLPart = "http://image.tmdb.org/t/p/w185";
    private Context context;
    public FirebaseAnalytics mFirebaseAnalytics;

    public Article mPosition;

    public FirstAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articleList = articles;
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
        final Article mPosition = articleList.get(position);
        String voteMoview = mPosition.getPublishedAt();
        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mPosition.id.toString());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        if (mPosition.getUrlToImage() != null) {
            Picasso.get().load(mPosition.getUrlToImage())

                    .resize(1400, 1165)
                    .centerCrop()
                    .placeholder(R.drawable.scope_placeholder)
                    .into(movieViewHolder.img);
            movieViewHolder.titleMain.setText(mPosition.getTitle());

        }
        movieViewHolder.img.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (mPosition.getUrlToImage() != null) {
                    Intent intent = new Intent(context, DeatailsActivity.class)
                            .putExtra("parcelable_extra_details", (Parcelable) mPosition);
                    context.startActivity(intent);
                }
            }
        });

        Log.e("p", position + "");
    }

    @Override
    public int getItemCount() {
        if (null == articleList) return 0;
        return articleList.size();
    }

    public void setMovieList(List<Article> list) {
        articleList = list;
        notifyDataSetChanged();
    }


    static class movieViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView titleMain;
        public TextView rate;

        public movieViewHolder(View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.iv_item);
            titleMain = itemView.findViewById(R.id.tv_main_title);
        }
    }
}
