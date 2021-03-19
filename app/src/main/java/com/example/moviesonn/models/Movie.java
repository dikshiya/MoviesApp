package com.example.moviesonn.models;

import com.example.moviesonn.Utils.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {
    private static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w500";

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("original_title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private float averageVote;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("id")
    private int movieId;

    private boolean wishlisted;

    public Movie(String title, String releaseDate, String poster, int movieId, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.movieId = movieId;
        this.overview = overview;
    }

    public boolean isWishlisted() {
        return wishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        this.wishlisted = wishlisted;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getBackdrop() {
        if (Utils.isnotNullorEmpty(backdrop)) return IMAGE_URL_PREFIX.concat(backdrop);
        else return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterURLwithPrefix() {
        if (Utils.isnotNullorEmpty(poster)) return IMAGE_URL_PREFIX.concat(poster);
        else return poster;
    }

    public String getPosterUrl() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(float averageVote) {
        this.averageVote = averageVote;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
