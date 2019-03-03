package com.example.ashleighwilson.booksearch.service.response;

public class Request {

    boolean authentication;
    String key;
    String method;

    @Override
    public String toString() {
        return "Request{" +
                "authentication=" + authentication +
                ", key='" + key + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
