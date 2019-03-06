package com.example.ashleighwilson.booksearch;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;

import javax.inject.Inject;

public class PreferenceUser {

    @Inject
    SharedPreferences sp;

    private static final String TAG = PreferenceUser.class.getSimpleName();

    private static final String CURRENT_USER_PREFS = "current_user";
    public static final String REQUEST_TOKEN = "request_token";
    public static final String REQUEST_SECRET = "request_secret";
    public static final String TOKEN = "oauth_token";
    public static final String TOKEN_SECRET = "oauth_secret";

    public PreferenceUser() {
        Injector.getInstance().inject(this);
    }

    public AuthUser getCurrentUser() {
        String userJson = sp.getString(CURRENT_USER_PREFS, null);
        if (userJson == null) {
            return null;
        }
        return AuthUser.fromJson(userJson);
    }

    public boolean setCurrentUser(AuthUser currentUser) {
        String userJson = null;
        if (currentUser != null) {
            Log.i(TAG, "gson: " + currentUser);
            userJson = currentUser.toJson();
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CURRENT_USER_PREFS, userJson);
        return editor.commit();
    }

    public boolean setTokens(String token, String secret) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN, token);
        editor.putString(TOKEN_SECRET, secret);
        return editor.commit();
    }

    public String getToken() {
        return sp.getString(TOKEN, null);
    }

    public String getSecret() {
        return sp.getString(TOKEN_SECRET, null);
    }
}
