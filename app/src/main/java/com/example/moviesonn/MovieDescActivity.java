package com.example.moviesonn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesonn.Utils.Constants;
import com.example.moviesonn.Utils.Utils;
import com.example.moviesonn.adapters.ReviewAdapter;
import com.example.moviesonn.adapters.TrailerAdapter;
import com.example.moviesonn.models.Movie;
import com.example.moviesonn.models.ReviewResponse;
import com.example.moviesonn.models.Reviews;
import com.example.moviesonn.models.Trailer;
import com.example.moviesonn.models.TrailerResponse;
import com.example.moviesonn.network.ApiClient;
import com.example.moviesonn.network.ApiClientService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDescActivity extends AppCompatActivity {
    private static final String INTENT_URL = "https://www.youtube.com/watch?v=%1$s";
    private final ArrayList<Trailer> trailers = new ArrayList<>();
    private final ArrayList<Reviews> reviews = new ArrayList<>();
    private AppCompatImageView ivPoster;
    private AppCompatImageView ivBackground;
    private AppCompatTextView tvMovieTitle;
    private AppCompatTextView tvReleaseDate;
    private AppCompatTextView tvTotalVotes;
    private AppCompatRatingBar rbAverageVotes;
    private AppCompatTextView tvOverview;
    private AppCompatTextView tvDescTrailerHeader;
    private AppCompatTextView tvDescReviewHeader;
    private RecyclerView rvTrailers;
    private RecyclerView rvReviews;
    private Movie mMovie;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_desc);
        mMovie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_ID);

        init();
        initReviewAdapter();
        initTrailerAdapter();
        getDetails(mMovie);
        getMovieTrailer();
        getMovieReviews();
    }

    private void init() {
        ivPoster = findViewById(R.id.iv_movie_poster);
        ivBackground = findViewById(R.id.iv_desc_activity_movie_background);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvTotalVotes = findViewById(R.id.tv_total_votes);
        tvOverview = findViewById(R.id.tv_overview);
        tvDescTrailerHeader = findViewById(R.id.tv_desc_activity_trailer_head);
        rbAverageVotes = findViewById(R.id.rb_average_votes);
        rvTrailers = findViewById(R.id.rv_trailers);
        rvReviews = findViewById(R.id.rv_reviews);
        tvDescReviewHeader = findViewById(R.id.tv_desc_activity_review_head);
    }

    private void initReviewAdapter() {
        rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reviewAdapter = new ReviewAdapter(reviews);
        rvReviews.setAdapter(reviewAdapter);
    }

    private void initTrailerAdapter() {
        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerAdapter = new TrailerAdapter(this, trailers);
        rvTrailers.setAdapter(trailerAdapter);
        trailerAdapter.setOnItemClickListener(position -> {
            Trailer trailer = trailers.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(INTENT_URL, trailer.getKey())));
            if (getPackageManager().resolveActivity(intent, 0) != null) {
                startActivity(intent);
            }
        });
    }

    private void getMovieTrailer() {

        ApiClientService trailerService = ApiClient.getInstance().createService(ApiClientService.class);
        trailerService.getTrailers(mMovie.getMovieId(), Constants.API_KEY).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, @NotNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResultsTrailer() != null) {
                            if (response.body().getResultsTrailer().isEmpty()) {
                                tvDescTrailerHeader.setVisibility(View.GONE);
                            }
                            trailers.clear();
                            trailers.addAll(response.body().getResultsTrailer());
                            trailerAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Utils.showToast("Something went wrong. Please try again later", MovieDescActivity.this);
                }
            }

            @Override
            public void onFailure(@NotNull Call<TrailerResponse> call, @NotNull Throwable t) {
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Please try again later.", MovieDescActivity.this);
            }
        });
    }

    private void getMovieReviews() {
        int id = mMovie.getMovieId();
        ApiClientService reviewService = ApiClient.getInstance().createService(ApiClientService.class);
        reviewService.getReviews(id, Constants.API_KEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResultsReview() != null) {
                            if (response.body().getResultsReview().isEmpty()) {
                                tvDescReviewHeader.setVisibility(View.GONE);
                            }
                            reviews.clear();
                            reviews.addAll(response.body().getResultsReview());
                            reviewAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Utils.showToast("Something went wrong. Please try again later", MovieDescActivity.this);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReviewResponse> call, @NotNull Throwable t) {
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Please try again later.", MovieDescActivity.this);
            }
        });
    }

    private void getDetails(Movie movieDetails) {
        if (movieDetails != null) {
            if (movieDetails.getBackdrop() != null) {
                Glide.with(this).load(movieDetails.getBackdrop()).transform(new BlurTransformation(22)).into(ivBackground);
            }
            if (movieDetails.getPosterURLwithPrefix() != null) {
                Utils.displayImage(this, movieDetails.getPosterURLwithPrefix(), ivPoster);
            }

            if (movieDetails.getTitle() != null) {
                tvMovieTitle.setText(movieDetails.getTitle());
            }

            if (movieDetails.getReleaseDate() != null) {
                tvReleaseDate.setText(movieDetails.getReleaseDate());
            }

            if (movieDetails.getAverageVote() != 0.0) {
                rbAverageVotes.setRating(movieDetails.getAverageVote() / 2);
            }

            if (movieDetails.getVoteCount() != 0) {
                tvTotalVotes.setText(String.valueOf(movieDetails.getVoteCount()));
            } else {
                String noVotes = "No votes";
                tvTotalVotes.setText(noVotes);
            }

            if (movieDetails.getOverview() != null) {
                tvOverview.setText(movieDetails.getOverview());
            }
        }
    }
}