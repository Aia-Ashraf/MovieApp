package com.example.aiaa.movieapp1;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private Context Mcontext;
    private List<ReviewsModel> reviewsModelList;
    public ReviewsModel reviewsModel;


    public ReviewsAdapter(Context context, List<ReviewsModel> reviewsModelList) {

        this.Mcontext = context;
        this.reviewsModelList = reviewsModelList;
        reviewsModel = null;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TextView textAuther = (TextView) parent.findViewById(R.id.tvAuther);
        TextView textContent = (TextView) parent.findViewById(R.id.tvContent);

        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {
        final ReviewsModel reviewsModelposition =reviewsModelList.get(position);
        holder.textView1.setText(reviewsModelposition.getAuthor());
        holder.textView2.setText(reviewsModelposition.getContent());

    }



    @Override
    public int getItemCount() {

        if (null == reviewsModelList) return 0;
        return reviewsModelList.size();
    }

    public void setMovieReviewList(List<ReviewsModel> reviewList) {
        reviewsModelList = reviewList;
        notifyDataSetChanged();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        public TextView textView2;


        public ReviewsViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.tvAuther);
            Log.d("Recycler Adapter", "title ");
            textView2 = itemView.findViewById(R.id.tvContent);


        }
    }
}
