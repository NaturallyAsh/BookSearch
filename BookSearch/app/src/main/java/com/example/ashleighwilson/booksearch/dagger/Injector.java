package com.example.ashleighwilson.booksearch.dagger;

import android.app.Application;

import com.codepath.oauth.OAuthBaseClient;
import com.example.ashleighwilson.booksearch.LoginActivity;
import com.example.ashleighwilson.booksearch.MainActivity;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthAccessTokenRequest;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthRequestTokenRequest;

public class Injector implements ApplicationComponent {

    private static ApplicationComponent component;
    private static Injector injector;

    public static Injector getInstance() {
        if (injector == null) {
            injector = new Injector();
        }
        return injector;
    }

    public void init(Application application) {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    @Override
    public void inject(Application application) {
        component.inject(application);
    }

    @Override
    public void inject(PreferenceUser preferenceUser) {
        component.inject(preferenceUser);
    }

    @Override
    public void inject(MainActivity mainActivity) {
        component.inject(mainActivity);
    }

    @Override
    public void inject(LoginActivity loginActivity) {
        component.inject(loginActivity);
    }

    @Override
    public void inject(AuthUser authUser) {
        component.inject(authUser);
    }

    @Override
    public void inject(OAuthRequestTokenRequest request) {
        component.inject(request);
    }

    @Override
    public void inject(OAuthAccessTokenRequest request) {
        component.inject(request);
    }
}
