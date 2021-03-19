package com.example.moviesonn.Utils;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

public class Utils {
    public static boolean isnotNullorEmpty(String string) {
        return string != null && !string.trim().isEmpty();
    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayImage(Context context, String url, AppCompatImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

}
