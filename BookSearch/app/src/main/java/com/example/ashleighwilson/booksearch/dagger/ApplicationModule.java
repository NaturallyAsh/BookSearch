package com.example.ashleighwilson.booksearch.dagger;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.ashleighwilson.booksearch.BuildConfig;
import com.example.ashleighwilson.booksearch.PreferenceUser;
import com.example.ashleighwilson.booksearch.data.MySimpleXMLConverter;
import com.example.ashleighwilson.booksearch.data.SimpleXmlConverterFactory;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpOAuthConsumer;
import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpOAuthProvider;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.OkHttpOAuthConsumer2;
import com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2.SigningInterceptor;
import com.example.ashleighwilson.booksearch.service.Oauth.RetrofitHttpOAuthConsumer;
import com.example.ashleighwilson.booksearch.service.Oauth.RetrofitSigningOkClient;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApi;
import com.example.ashleighwilson.booksearch.service.response.GoodreadsApiRetro2;
import com.example.ashleighwilson.booksearch.service.response.GoogleBooksApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private static final String TAG = ApplicationModule.class.getSimpleName();

    private static final String PREFS_NAME = "BookSearchPrefs";
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    PreferenceUser providePreferenceUser() {
        return new PreferenceUser();
    }

    @Provides
    AuthUser provideCurrentUser(PreferenceUser preferenceUser) {
        AuthUser user = preferenceUser.getCurrentUser();
        if (user == null) {
            user = new AuthUser();
        }
        return user;
    }

    @Provides
    @Singleton
    public GoodreadsApi provideGoodreadsApi(PreferenceUser preferenceUser) {
        RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(
                BuildConfig.Goodreads_Api_Key,
                BuildConfig.Goodreads_Secret
        );

        oAuthConsumer.setTokenWithSecret(
                preferenceUser.getToken(),
                preferenceUser.getSecret()
        );

       RestAdapter restAdapter = new RestAdapter.Builder()
               .setClient(new RetrofitSigningOkClient(oAuthConsumer))
               .setEndpoint(BuildConfig.Goodreads_Base_Url)
               .setConverter(new MySimpleXMLConverter())
               .build();

        return restAdapter.create(GoodreadsApi.class);
    }

    @Provides
    @Singleton
    public GoodreadsApiRetro2 provideRetro2GoodreadsApi(PreferenceUser preferenceUser) {
        OkHttpOAuthConsumer2 oAuthConsumer = new OkHttpOAuthConsumer2(
                BuildConfig.Goodreads_Api_Key,
                BuildConfig.Goodreads_Secret
        );

        oAuthConsumer.setTokenWithSecret(
                preferenceUser.getToken(),
                preferenceUser.getSecret()
        );

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(oAuthConsumer))
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.Goodreads_Base_Url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        return retrofit.create(GoodreadsApiRetro2.class);
    }

    @Provides
    @Singleton
    public GoogleBooksApi provideGoogleBooksApi() {

        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.Googlebooks_Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GoogleBooksApi.class);
    }

    @Provides
    @Singleton
    OAuthProvider provideOAuthProvider() {
        return new OkHttpOAuthProvider(
                BuildConfig.Goodreads_Base_Url + "/oauth/request_token",
                BuildConfig.Goodreads_Base_Url + "/oauth/access_token",
                BuildConfig.Goodreads_Base_Url + "/oauth/authorize?mobile=1"
        );
    }

    @Provides
    @Singleton
    OAuthConsumer provideOAuthConsumer() {
        return new OkHttpOAuthConsumer(
                BuildConfig.Goodreads_Api_Key,
                BuildConfig.Goodreads_Secret
        );
    }
}
