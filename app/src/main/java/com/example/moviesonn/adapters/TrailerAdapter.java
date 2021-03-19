package com.example.moviesonn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesonn.OnItemClickListener;
import com.example.moviesonn.R;
import com.example.moviesonn.Utils.Utils;
import com.example.moviesonn.models.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.VHTrailer> {
    private ArrayList<Trailer> trailers;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public VHTrailer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHTrailer(LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VHTrailer holder, int position) {
        Trailer trailer = trailers.get(position);
        if (trailer != null) {
            if (trailer.getKeyWithPrefix() != null) {
                Utils.displayImage(context, trailer.getKeyWithPrefix(), holder.ivthumbnail);
            }
        }
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemClick(holder.getAdapterPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class VHTrailer extends RecyclerView.ViewHolder {
        private final AppCompatImageView ivthumbnail;

        public VHTrailer(@NonNull View itemView) {
            super(itemView);
            ivthumbnail = itemView.findViewById(R.id.iv_trailer_item_thumbnail);
        }
    }
}
