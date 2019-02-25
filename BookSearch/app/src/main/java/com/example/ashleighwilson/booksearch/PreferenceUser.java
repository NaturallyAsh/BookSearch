package com.example.ashleighwilson.booksearch;

import android.content.SharedPreferences;

public class PreferenceUser {

    private static final String TAG = PreferenceUser.class.getSimpleName();

    SharedPreferences sp;
    SharedPreferences.Editor editor = sp.edit();

    private static final String CURRENT_USER_PREFS = "current_user";
    public static final String TOKEN = "oauth_token";
    public static final String TOKEN_SECRET = "oauth_secret";

    public boolean setTokens(String token, String secret) {
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
