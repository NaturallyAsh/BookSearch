package com.example.ashleighwilson.booksearch.service.Oauth;

import android.util.Log;

import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.service.response.AuthUserResponse;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.UserShowResponse;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

import dagger.Lazy;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import retrofit.RetrofitError;

public class OAuthAccessTokenRequest extends SpiceRequest<AuthUser> {

    private static final String TAG = OAuthAccessTokenRequest.class.getSimpleName();

    private final String oauthToken;

    @Inject
    Lazy<GoodreadsApi> goodreadsAPI;

    @Inject
    OAuthProvider oAuthProvider;

    @Inject
    OAuthConsumer oAuthConsumer;

    @Inject
    PreferenceUser preferenceUser;

    public OAuthAccessTokenRequest(String oauthToken) {
        super(AuthUser.class);

        Injector.getInstance().inject(this);

        this.oauthToken = oauthToken;
    }

    @Override
    public AuthUser loadDataFromNetwork() throws Exception {
        return getAccessToken(oauthToken);
    }

    private AuthUser getAccessToken(String oauth_token) {
        try {
            oAuthProvider.retrieveAccessToken(oAuthConsumer, oauth_token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        preferenceUser.setTokens(
                oAuthConsumer.getToken(),
                oAuthConsumer.getTokenSecret()
        );

        try {
            AuthUserResponse authUserResponse = goodreadsAPI.get().auth_user();
            AuthUser registeredUser = authUserResponse.getUser();
            Log.i(TAG, "registered user id: " + authUserResponse.getUser().getId());

            UserShowResponse userShowResponse = goodreadsAPI.get().user_show(registeredUser.getId());
            //return authUserResponse.getUser();
            return userShowResponse.getUser();
        } catch (RetrofitError retrofitError) {
            retrofitError.printStackTrace();
            //Log.i(TAG, "retrofit error");
            return null;
        }
    }
}
