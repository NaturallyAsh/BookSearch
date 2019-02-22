package com.example.ashleighwilson.booksearch.service;

import android.content.Context;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthBaseClient;
import com.github.scribejava.core.builder.api.BaseApi;

public class GoodreadsClient extends OAuthBaseClient {

    public static final BaseApi API_INSTANCE = GoodreadsApi.getInstance();
    public static final String BASE_URL = BuildConfig.Goodreads_Base_Url;
    public static final String CONSUMER_KEY = BuildConfig.Goodreads_Api_Key;
    public static final String CONSUMER_SECRET = BuildConfig.Goodreads_Secret;
    public static final String CALLBACK_URL = BuildConfig.Goodreads_Callback_URL;

    public GoodreadsClient(Context c) {
        super(c, API_INSTANCE, BASE_URL, CONSUMER_KEY, CONSUMER_SECRET, CALLBACK_URL);
    }
}
