package com.example.ashleighwilson.booksearch.dagger;

import android.app.Application;

import com.codepath.oauth.OAuthBaseClient;
import com.example.ashleighwilson.booksearch.LoginActivity;
import com.example.ashleighwilson.booksearch.MainActivity;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthAccessTokenRequest;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthRequestTokenRequest;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    void inject(Application application);
    void inject(PreferenceUser preferenceUser);
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(AuthUser authUser);
    void inject(OAuthRequestTokenRequest request);
    void inject(OAuthAccessTokenRequest request);
}
