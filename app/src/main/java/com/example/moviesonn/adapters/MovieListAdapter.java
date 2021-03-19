package com.example.moviesonn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moviesonn.OnItemClickListener;
import com.example.moviesonn.R;
import com.example.moviesonn.Utils.Utils;
import com.example.moviesonn.models.Movie;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesVH> {
    private ArrayList<Movie> results;
    private Context context;
    private OnItemClickListener onMovieClickListener;

    public MovieListAdapter(ArrayList<Movie> results, Context context) {
        this.results = results;
        this.context = context;

    }

    @NonNull
    @Override
    public MoviesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesVH holder, int position) {
        Movie movie = results.get(position);
        if (movie != null) {
            if (movie.getPosterURLwithPrefix() != null) {
                Utils.displayImage(context, movie.getPosterURLwithPrefix(), holder.movieImage);
            }
            if (movie.getTitle() != null) {
                holder.movieTitle.setText(movie.getTitle());
            }
            if (movie.getReleaseDate() != null) {
                holder.movieReleaseDate.setText(movie.getReleaseDate());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        onMovieClickListener.OnItemClick(holder.getAdapterPosition());
                    }
                }
            });

            if (movie.isWishlisted()) {
                holder.wishlistChecked.isAnimating();
            } else {
                holder.wishlistChecked.pauseAnimation();
            }

            holder.wishlistChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!movie.isWishlisted()) {
                        holder.wishlistChecked.setSpeed(1);
                        holder.wishlistChecked.playAnimation();
                        movie.setWishlisted(true);
                    } else {
                        holder.wishlistChecked.setSpeed(-1);
                        holder.wishlistChecked.playAnimation();
                        movie.setWishlisted(false);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MoviesVH extends RecyclerView.ViewHolder {
        private AppCompatImageView movieImage;
        private AppCompatTextView movieTitle;
        private AppCompatTextView movieReleaseDate;
        private LottieAnimationView wishlistChecked;

        public MoviesVH(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie_image);
            movieTitle = itemView.findViewById(R.id.tv_movie_title);
            movieReleaseDate = itemView.findViewById(R.id.tv_release_date);
            wishlistChecked = itemView.findViewById(R.id.wishlist_heart_movie_item);
        }
    }
}
