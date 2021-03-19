package com.example.moviesonn;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesonn.Utils.Constants;
import com.example.moviesonn.Utils.Utils;
import com.example.moviesonn.adapters.MovieListAdapter;
import com.example.moviesonn.models.MovieResponse;
import com.example.moviesonn.network.ApiClient;
import com.example.moviesonn.network.ApiClientService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements movieFragmentCallback {
    public static final int PAGE = 2;
    public static final String MOVIE_ID = "movie_id";
    private final ArrayList<com.example.moviesonn.models.Movie> mResults = new ArrayList<>();
    private AppCompatEditText mEtMovieName;
    private AppCompatImageView mIvSearch;
    private AppCompatImageView mIvClear;
    private RecyclerView mRvMovies;
    private ProgressBar mProgressBar;
    private AppCompatTextView mTvPopularTitle;
    private AppCompatTextView mTvEmptyActivity;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        getPopularMovies();
        setOnClickListeners();
    }

    private void initView() {
        mEtMovieName = findViewById(R.id.et_movie_name);
        mIvSearch = findViewById(R.id.btn_search);
        mIvClear = findViewById(R.id.btn_clear);
        mRvMovies = findViewById(R.id.rv_movies);
        mProgressBar = findViewById(R.id.progressBar);
        mTvPopularTitle = findViewById(R.id.tv_popular_title);
        mTvEmptyActivity = findViewById(R.id.tv_main_activity_empty);
    }

    private void initAdapter() {
        mRvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieListAdapter(mResults, this);
        mRvMovies.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> MovieDetailBottomFragment.newInstance(mResults.get(position))
                .show(getSupportFragmentManager(), MovieDetailBottomFragment.EXTRA_MOVIE));
    }


    private void setOnClickListeners() {
        mIvSearch.setOnClickListener(v -> searchMovies());
        mIvClear.setOnClickListener(v -> clearSearch());
    }

    private void getPopularMovies() {
        mProgressBar.setVisibility(View.VISIBLE);
        ApiClientService popService = ApiClient.getInstance().createService(ApiClientService.class);
        popService.getPopularMovies(Constants.API_KEY, PAGE).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    hideProgress();
                    if (response.body() != null) {
                        if (response.body().getResultsMovie() != null) {
                            mResults.clear();
                            mResults.addAll(response.body().getResultsMovie());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else
                    Utils.showToast("Something went wrong. Please try again later", MainActivity.this);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, @NotNull Throwable t) {
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Something went wrong. Please check you internet connection", MainActivity.this);
            }
        });
    }

    private void searchMovies() {
        if (!Utils.isnotNullorEmpty(mEtMovieName.getText().toString())) {
            return;
        }

        showProgress();
        ApiClientService searchService = ApiClient.getInstance().createService(ApiClientService.class);
        searchService.getSearchedMovies(Constants.API_KEY, mEtMovieName.getText().toString()).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    hideProgress();
                    if (response.body() != null) {
                        if (response.body().getResultsMovie() != null) {
                            if (response.body().getResultsMovie().isEmpty()) {
                                mTvEmptyActivity.setVisibility(View.VISIBLE);
                            }
                            mResults.clear();
                            mResults.addAll((Collection<? extends com.example.moviesonn.models.Movie>) response.body().getResultsMovie());
                            mAdapter.notifyDataSetChanged();
                            mTvPopularTitle.setVisibility(View.GONE);
                        }
                    }
                } else {
                    hideProgress();
                    Utils.showToast("Please enter a Movie Name to search", MainActivity.this);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, Throwable t) {
                hideProgress();
                Utils.showToast(t.getMessage() != null ? t.getMessage() : "Something went wrong. Please check your internet connection", MainActivity.this);
            }
        });
    }

    private void clearSearch() {
        mEtMovieName.setText("");
        mResults.clear();
        mAdapter.notifyDataSetChanged();
        mRvMovies.setVisibility(View.VISIBLE);
        getPopularMovies();
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvPopularTitle.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.GONE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mRvMovies.setVisibility(View.VISIBLE);
        mTvPopularTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentClick(com.example.moviesonn.models.Movie movie) {
        String message = "Retrieving data for Movie Id ";
        Utils.showToast(message.concat(String.valueOf(movie.getMovieId())), MainActivity.this);
    }
}