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

public class TrailersMovieAdapter extends RecyclerView.Adapter<TrailersMovieAdapter.TrailersViewHolder> {
    private Context Mcontext;
    private List<MovieTrailer> movieTrailersList;
    public MovieTrailer MmovieTrailer;
    private MediaPlayer mp;
    private MediaController mc ;
    private String Vurl;


    public TrailersMovieAdapter(Context context, List<MovieTrailer> movieTrailers) {

        this.Mcontext = context;
        this.movieTrailersList = movieTrailers;
        MmovieTrailer = null;
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TextView textView = (TextView) parent.findViewById(R.id.tvName);
        ImageView imageView =(ImageView) parent.findViewById(R.id.vvTrailer);

        return new TrailersMovieAdapter.TrailersViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        final MovieTrailer movieTrailerPosition = movieTrailersList.get(position);
        holder.textView.setText(movieTrailerPosition.getName());
        Log.d("Recycler Adapter", "title ");
        Vurl = "https://www.youtube.com/watch?v="+movieTrailerPosition.getKey();
        final Uri uri =Uri.parse(Vurl);
        Log.d("Recycler Adapter", Vurl);

       // holder.videoView.setVideoURI(uri);
       // holder.videoView.start();

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (movieTrailerPosition.getId() != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Vurl));
                    Mcontext.startActivity(intent);
                //}
            }
        });

        Log.e("p", position + "");

        Log.d("Recycler Adapter", "title "+uri);
    }

    @Override
    public int getItemCount() {

        if (null == movieTrailersList) return 0;
        return movieTrailersList.size();
    }

    public void setMovieTrailersListList(List<MovieTrailer> TrailersList) {
        movieTrailersList = TrailersList;
        notifyDataSetChanged();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ImageView imageView;


        public TrailersViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvName);
            Log.d("Recycler Adapter", "title ");
            imageView = itemView.findViewById(R.id.vvTrailer);


            mc = new MediaController(Mcontext);

        }
    }
}