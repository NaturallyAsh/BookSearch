package com.example.ashleighwilson.booksearch.dagger;

import android.app.Application;

import com.example.ashleighwilson.booksearch.BookDetailFragment;
import com.example.ashleighwilson.booksearch.loaders.AddToShelfLoader;
import com.example.ashleighwilson.booksearch.loaders.AudiobookImageLoader;
import com.example.ashleighwilson.booksearch.loaders.BookDetailsLoader;
import com.example.ashleighwilson.booksearch.loaders.ReadImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SearchBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesBookLoader;
import com.example.ashleighwilson.booksearch.loaders.SeriesImageLoader;
import com.example.ashleighwilson.booksearch.loaders.SimilarImageLoader;
import com.example.ashleighwilson.booksearch.loaders.WantedImageLoader;
import com.example.ashleighwilson.booksearch.loaders.CurrentlyReadingLoader;
import com.example.ashleighwilson.booksearch.LoginActivity;
import com.example.ashleighwilson.booksearch.MainActivity;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.loaders.ToReadBookLoader;
import com.example.ashleighwilson.booksearch.UserFragment;
import com.example.ashleighwilson.booksearch.loaders.WantToReadLoader;
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
    void inject(UserFragment userFragment);
    void inject(CurrentlyReadingLoader currentlyReadingLoader);
    void inject(WantToReadLoader wantToReadLoader);
    void inject(ToReadBookLoader toReadBookLoader);
    void inject(WantedImageLoader wantedImageLoader);
    void inject(ReadImageLoader readImageLoader);
    void inject(BookDetailsLoader bookDetailsLoader);
    void inject(SeriesBookLoader seriesBookLoader);
    void inject(SeriesImageLoader seriesImageLoader);
    void inject(SimilarImageLoader similarImageLoader);
    void inject(AddToShelfLoader addToShelfLoader);
    void inject(BookDetailFragment bookDetailFragment);
    void inject(SearchBookLoader searchBookLoader);
    void inject(AudiobookImageLoader audiobookImageLoader);
}
