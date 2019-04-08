package com.example.ashleighwilson.booksearch.service.response;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoodreadsApiRetro2 {
    @GET("/review/list/{id}.xml")
    Call<ReviewsAndShelfResponse> review_list(@Path("id") long id,
                                              @Query("v") int v,
                                              @Query("shelf") String shelf);

    @GET("/book/show/{id}?format=xml")
    Call<BookShowResponse> book_show(@Path("id") String id,
                                     @Query("key") String key);

    @GET("/series/show/{id}.xml")
    Call<SeriesResponse> series_show(@Path("id") String series_id,
                                     @Query("key") String api_key);

    @GET("/search/index.xml")
    Call<SearchResponse> search_index(@Query("key") String api_key,
                                      @Query("q") String query);

    @POST("/shelf/add_to_shelf")
    Call<Request> add_to_shelf(@Query("name") String shelf_name,
                                    @Query("book_id") String book_id,
                                    @Query("key") String key,
                                    @Query("format") String xml);
}
