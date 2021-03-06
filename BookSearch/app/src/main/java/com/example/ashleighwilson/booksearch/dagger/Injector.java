package com.example.ashleighwilson.booksearch.dagger;

import android.app.Application;

import com.example.ashleighwilson.booksearch.BookDetailFragment;
import com.example.ashleighwilson.booksearch.loaders.AddToShelfLoader;
import com.example.ashleighwilson.booksearch.loaders.AudiobookImageLoader;
import com.example.ashleighwilson.booksearch.loaders.BookDetailsLoader;
import com.example.ashleighwilson.booksearch.loaders.SearchBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SimilarImageLoader;
import com.example.ashleighwilson.booksearch.loaders.ToReadBookLoader;
import com.example.ashleighwilson.booksearch.loaders.ReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.WantedImageLoader;
import com.example.ashleighwilson.booksearch.LoginActivity;
import com.example.ashleighwilson.booksearch.MainActivity;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.loaders.CurrentlyReadingLoader;
import com.example.ashleighwilson.booksearch.UserFragment;
import com.example.ashleighwilson.booksearch.loaders.WantToReadLoader;
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

    @Override
    public void inject(UserFragment userFragment) {
        component.inject(userFragment);
    }

    @Override
    public void inject(CurrentlyReadingLoader currentlyReadingLoader) {
        component.inject(currentlyReadingLoader);
    }

    @Override
    public void inject(WantToReadLoader wantToReadLoader) {
        component.inject(wantToReadLoader);
    }

    @Override
    public void inject(ToReadBookLoader toReadBookLoader) {
        component.inject(toReadBookLoader);
    }

    @Override
    public void inject(WantedImageLoader wantedImageLoader) {
        component.inject(wantedImageLoader);
    }

    @Override
    public void inject(ReadImageLoader readImageLoader) {
        component.inject(readImageLoader);
    }

    @Override
    public void inject(BookDetailsLoader bookDetailsLoader) {
        component.inject(bookDetailsLoader);
    }

    @Override
    public void inject(SeriesBookLoader seriesBookLoader) {
        component.inject(seriesBookLoader);
    }

    @Override
    public void inject(SeriesImageLoader seriesImageLoader) {
        component.inject(seriesImageLoader);
    }

    @Override
    public void inject(SimilarImageLoader similarImageLoader) {
        component.inject(similarImageLoader);
    }

    @Override
    public void inject(AddToShelfLoader addToShelfLoader) {
        component.inject(addToShelfLoader);
    }

    @Override
    public void inject(BookDetailFragment bookDetailFragment) {
        component.inject(bookDetailFragment);
    }

    @Override
    public void inject(SearchBookLoader searchBookLoader) {
        component.inject(searchBookLoader);
    }

    @Override
    public void inject(AudiobookImageLoader audiobookImageLoader) {
        component.inject(audiobookImageLoader);
    }
}
