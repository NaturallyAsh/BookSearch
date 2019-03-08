package com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2;

import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpOAuthConsumer;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import oauth.signpost.exception.OAuthException;

public class SigningInterceptor implements Interceptor {

    private final OkHttpOAuthConsumer2 consumer;

    /**
     * Constructs a new {@code SigningInterceptor}.
     *
     * @param consumer the {@link OkHttpOAuthConsumer2} used to sign the requests.
     */
    public SigningInterceptor(OkHttpOAuthConsumer2 consumer) {
        this.consumer = consumer;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            return chain.proceed((Request) consumer.sign(request).unwrap());
        } catch (OAuthException e) {
            throw new IOException("Could not sign request", e);
        }
    }
}
