package com.example.ashleighwilson.booksearch.service.response;


import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GoodreadsApi {
    @GET("/api/auth_user")
    AuthUserResponse auth_user();

    @GET("/user/show/{id}.xml")
    UserShowResponse user_show(@Path("id") long id);

    @GET("/review/list/{id}?format=xml")
    ReviewListResponse review_list(@Path("id") long id
                                        //@Query("api_key") String api_key,
                                        );

    //https://www.goodreads.com/review/list/7429745.xml?key=dO3q5uQ45Bi0CT1RuAn9Dg&v=2
    //https://www.goodreads.com/review/list.xml
    //https://www.goodreads.com/user/show/7429745.xml?key=dO3q5uQ45Bi0CT1RuAn9Dg
}
