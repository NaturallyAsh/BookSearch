package com.example.ashleighwilson.booksearch.service.response;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoodreadsApiRetro2 {
    @GET("/review/list/{id}.xml")
    Call<ReviewsAndShelfResponse> review_list(@Path("id") long id,
                                              @Query("v") int v,
                                              @Query("shelf") String shelf);

    @GET("/book/show/{id}?format=xml")
    Call<BookShowResponse> book_show(@Path("id") String id);

    @GET("/series/show/{id}.xml")
    Call<SeriesResponse> series_show(@Path("id") String series_id,
                                     @Query("key") String api_key);
}
