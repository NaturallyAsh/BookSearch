package com.example.ashleighwilson.booksearch.service.response;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;

public interface GoodreadsApi {
    @GET("/api/auth_user")
    AuthUserResponse auth_user();

    @GET("/user/show/{id}.xml")
    UserShowResponse user_show(@Path("id") long id);
}
