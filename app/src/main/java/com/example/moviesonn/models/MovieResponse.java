package com.example.moviesonn.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieResponse implements Serializable {

    @SerializedName("results")
    private ArrayList<com.example.moviesonn.models.Movie> resultsMovie;

    public ArrayList<Movie> getResultsMovie() {
        return resultsMovie;
    }

    public void setResultsMovie(ArrayList<com.example.moviesonn.models.Movie> resultsMovie) {
        this.resultsMovie = resultsMovie;
    }

}
