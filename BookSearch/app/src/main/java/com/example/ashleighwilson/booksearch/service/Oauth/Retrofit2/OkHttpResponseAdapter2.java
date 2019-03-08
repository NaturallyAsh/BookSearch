package com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2;

import java.io.IOException;
import java.io.InputStream;

import oauth.signpost.http.HttpResponse;
import okhttp3.Response;

public class OkHttpResponseAdapter2 implements HttpResponse {

    private Response response;

    public OkHttpResponseAdapter2(Response response) {
        this.response = response;
    }

    @Override
    public int getStatusCode() throws IOException {
        return response.code();
    }

    @Override
    public String getReasonPhrase() throws Exception {
        return response.message();
    }

    @Override
    public InputStream getContent() throws IOException {
        return response.body().byteStream();
    }

    @Override
    public Object unwrap() {
        return response;
    }
}
