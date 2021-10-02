package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Involve inflating a layout from XML and returning the holder.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreate");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populate data into the item through holder.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBind" + position);
        //Get the movie at the position.
        Movie movie = movies.get(position);

        // Bind the movie data into the VH.
        holder.bind(movie);
    }

    // Return total count of the list.
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }

            Glide.with(context).load(imageURL).into(ivPoster);

//            // 1. register on click listener
//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                // 2. navigate to a new activity on tag
//                public void onClick(View view) {
//                    Intent i = new Intent(context, MovieDetailsActivity.class);
//
//                    context.startActivity(i);
//                }
//            });
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            // Sanity check to make sure position is valid.
            if (position != RecyclerView.NO_POSITION) {
                // Get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // Create an Intent to display MovieDetailsActivity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // Pass the movie as an extra serialized via Parcels.wrap()
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }

        }
    }
}
