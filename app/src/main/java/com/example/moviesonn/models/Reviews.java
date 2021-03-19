package com.example.moviesonn.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reviews implements Serializable {

    @SerializedName("author")
    private String authorName;

    @SerializedName("content")
    private String reviewContent;

    @SerializedName("updated_at")
    private String updatedAt;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getUpdatedAt() {
        return updatedAt.toString();
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
