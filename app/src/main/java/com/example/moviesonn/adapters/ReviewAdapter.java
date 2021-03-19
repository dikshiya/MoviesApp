package com.example.moviesonn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesonn.R;
import com.example.moviesonn.models.Reviews;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VHReview> {
    private final ArrayList<Reviews> reviewsArrayList;

    public ReviewAdapter(ArrayList<Reviews> reviewList) {
        this.reviewsArrayList = reviewList;
    }

    @NonNull
    @Override
    public VHReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHReview(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VHReview holder, int position) {
        Reviews reviews = reviewsArrayList.get(position);
        if (reviews != null) {
            if (reviews.getAuthorName() != null) {
                holder.authorName.setText(reviews.getAuthorName());
            }
            if (reviews.getReviewContent() != null) {
                holder.reviewContent.setText(reviews.getReviewContent());
            }
            if (reviews.getUpdatedAt() != null) {
                holder.reviewDate.setText(reviews.getUpdatedAt());
            }
        }
    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

    public class VHReview extends RecyclerView.ViewHolder {
        private AppCompatTextView authorName;
        private AppCompatTextView reviewContent;
        private AppCompatTextView reviewDate;

        public VHReview(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.tv_author_name);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
            reviewDate = itemView.findViewById(R.id.tv_upload_date);
        }
    }
}
