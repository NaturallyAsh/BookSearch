package com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import oauth.signpost.http.HttpRequest;
import okhttp3.Request;
import okio.Buffer;

public class OkHttpRequestAdapter2 implements HttpRequest {

    private Request request;

    /**
     * Constructs a new {@code OkHttpRequestAdapter}.
     * @param request the {@link Request} that is to be signed.
     */
    public OkHttpRequestAdapter2(Request request) {
        this.request = request;
    }

    @Override
    public Map<String, String> getAllHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        for (String key : request.headers().names()) {
            headers.put(key, request.header(key));
        }
        return headers;
    }

    @Override
    public String getContentType() {
        if (request.body() != null) {
            return (request.body().contentType() != null) ? request.body().contentType().toString() : null;
        }
        return null;
    }

    @Override
    public String getHeader(String key) {
        return request.header(key);
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        if (request.body() == null) {
            return null;
        }
        Buffer buf = new Buffer();
        request.body().writeTo(buf);
        return buf.inputStream();
    }

    @Override
    public String getMethod() {
        return request.method();
    }

    @Override
    public String getRequestUrl() {
        return request.url().toString();
    }

    @Override
    public void setHeader(String key, String value) {
        request = request.newBuilder().header(key, value).build();
    }

    @Override
    public void setRequestUrl(String url) {
        request = request.newBuilder().url(url).build();
    }

    @Override
    public Object unwrap() {
        return request;
    }

}
