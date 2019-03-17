package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.GoogleImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {
    @GET("/books/v1/volumes?")
    Call<GoogleImageResponse> get_images(@Query("q") String title,
                                         @Query("key") String key);

}
