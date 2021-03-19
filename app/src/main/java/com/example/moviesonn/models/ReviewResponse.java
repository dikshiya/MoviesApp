package com.example.moviesonn.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewResponse implements Serializable {
    @SerializedName("results")
    private ArrayList<Reviews> resultsReview;

    public ArrayList<Reviews> getResultsReview() {
        return resultsReview;
    }

    public void setResultsReview(ArrayList<Reviews> resultsReview) {
        this.resultsReview = resultsReview;
    }
}
