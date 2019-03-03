package com.example.ashleighwilson.booksearch.service;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.github.scribejava.core.builder.api.DefaultApi10a;

public class GoodreadsClientApi extends DefaultApi10a {

    public static final String BASE_URL_ENDPOINT = BuildConfig.Goodreads_Base_Url;
    public static final String OAUTH_REQUEST_TOKEN_URL = "/oauth/request_token";
    public static final String OAUTH_ACCESS_TOKEN_URL = "/oauth/access_token";
    public static final String OAUTH_AUTHORIZE_URL = "/oauth/authorize?mobile=1";

    public static GoodreadsClientApi getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final GoodreadsClientApi INSTANCE = new GoodreadsClientApi();
    }

    @Override
    public String getRequestTokenEndpoint() {
        return BASE_URL_ENDPOINT + OAUTH_REQUEST_TOKEN_URL;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return BASE_URL_ENDPOINT + OAUTH_ACCESS_TOKEN_URL;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return BASE_URL_ENDPOINT + OAUTH_AUTHORIZE_URL;
    }
}
