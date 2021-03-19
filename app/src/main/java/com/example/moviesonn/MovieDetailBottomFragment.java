package com.example.moviesonn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.moviesonn.models.Movie;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailBottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MovieDetailBottomFragment extends BottomSheetDialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String EXTRA_MOVIE = "param1";
    private Movie mMovie;
    private AppCompatImageView ivFragmentMovie;
    private AppCompatTextView tvMovieTitle;
    private AppCompatTextView tvReleaseDate;
    private AppCompatTextView tvOverview;
    private AppCompatRatingBar rbMovieRating;
    private movieFragmentCallback movieFragmentCallback;


    public MovieDetailBottomFragment() {
        // Required empty public constructor
    }

    public static MovieDetailBottomFragment newInstance(Movie movie) {              //Activity to fragment || fragment to fragment
        MovieDetailBottomFragment fragment = new MovieDetailBottomFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MOVIE, (Serializable) movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(v -> {
            if (movieFragmentCallback != null)
                movieFragmentCallback.onFragmentClick(mMovie);
            Intent i = new Intent(getActivity(), MovieDescActivity.class);
            i.putExtra(MainActivity.MOVIE_ID, mMovie);
            startActivity(i);
        });
        initView(view);
    }

    private void initView(View view) {
        //find view by ids here
        // do initview vala kaam "view.findviewbyid()"
        ivFragmentMovie = view.findViewById(R.id.iv_fragment_movie_poster);
        tvMovieTitle = view.findViewById(R.id.tv_fragment_movie_title);
        tvReleaseDate = view.findViewById(R.id.tv_fragment_release_date);
        tvOverview = view.findViewById(R.id.tv_fragment_overview);
        rbMovieRating = view.findViewById(R.id.rb_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null)
            mMovie = (Movie) getArguments().getSerializable(EXTRA_MOVIE);
        displayFragmentDetails(mMovie);
    }

    private void displayFragmentDetails(Movie movie) {
        if (movie != null) {
            if (movie.getTitle() != null)
                tvMovieTitle.setText(movie.getTitle());
            if (movie.getOverview() != null)
                tvOverview.setText(movie.getOverview());
            if (movie.getReleaseDate() != null)
                rbMovieRating.setRating(movie.getAverageVote() / 2);
            if (movie.getPosterURLwithPrefix() != null)
                Glide.with(getActivity()).load(movie.getPosterURLwithPrefix()).into(ivFragmentMovie);
            if (movie.getReleaseDate() != null)
                tvReleaseDate.setText(movie.getReleaseDate());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {  // wasn't required
        super.onAttach(context);
        movieFragmentCallback = (movieFragmentCallback) context;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
