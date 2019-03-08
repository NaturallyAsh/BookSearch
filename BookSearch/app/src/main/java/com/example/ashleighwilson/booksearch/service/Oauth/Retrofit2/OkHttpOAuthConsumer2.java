package com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2;

import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpRequestAdapter;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;
import okhttp3.Request;

public class OkHttpOAuthConsumer2 extends AbstractOAuthConsumer {

    public OkHttpOAuthConsumer2(String consumerKey, String consumerSecret) {
        super(consumerKey, consumerSecret);
    }

    @Override
    protected HttpRequest wrap(Object request) {
        if (!(request instanceof Request)) {
            throw new IllegalArgumentException("This consumer expects requests of type " + Request.class.getCanonicalName());
        }
        return new OkHttpRequestAdapter2((Request) request);
    }
}
