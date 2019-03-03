package com.example.ashleighwilson.booksearch.service.Oauth;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

public class OAuthRequestTokenRequest extends SpiceRequest<String> {

    private static final String TAG = OAuthRequestTokenRequest.class.getSimpleName();

    @Inject
    OAuthProvider oAuthProvider;

    @Inject
    OAuthConsumer oAuthConsumer;

    public OAuthRequestTokenRequest() {
        super(String.class);

        Injector.getInstance().inject(this);
    }

    @Override
    public String loadDataFromNetwork() throws Exception {
        return getRequestToken();
    }

    public String getRequestToken() {
        String authUrl = null;

        try {
            authUrl = oAuthProvider.retrieveRequestToken(
                    oAuthConsumer,
                    OAuth.OUT_OF_BAND,
                    "mobile=1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authUrl;
    }
}
