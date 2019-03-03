package com.example.ashleighwilson.booksearch.service.Oauth;

import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

import oauth.signpost.http.HttpResponse;

public class OkHttpResponseAdapter implements HttpResponse {

    private Response response;

    public OkHttpResponseAdapter(Response response) {
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
    public Response unwrap() {
        return response;
    }
}
