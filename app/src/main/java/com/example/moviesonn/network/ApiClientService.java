package com.example.moviesonn.network;

import com.example.moviesonn.models.MovieResponse;
import com.example.moviesonn.models.ReviewResponse;
import com.example.moviesonn.models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClientService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key,
                                         @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String api_key,
                                          @Query("query") String search_query);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(@Path("movie_id") int movie_id,
                                      @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id") int movie_id,
                                    @Query("api_key") String api_key);
}
