package com.example.moviesonn.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TrailerResponse implements Serializable {
    @SerializedName("results")
    private ArrayList<Trailer> resultsTrailer;

    public ArrayList<Trailer> getResultsTrailer() {
        return resultsTrailer;
    }

    public void setResultsTrailer(ArrayList<Trailer> resultsTrailer) {
        this.resultsTrailer = resultsTrailer;
    }
}
