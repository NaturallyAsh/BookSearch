package com.example.ashleighwilson.booksearch.service;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.codepath.utils.GenericsUtil;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthBaseClient;

import androidx.fragment.app.FragmentActivity;

public abstract class OAuthLoginActivity <T extends OAuthBaseClient> extends FragmentActivity implements
        OAuthBaseClient.OAuthAccessHandler, OAuthBaseClient.OnReceivedAuthUrl {

    private T client;
    private static final String TAG = OAuthLoginActivity.class.getSimpleName();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();


        Class<T> clientClass = getClientClass();

        try {
            client = (T) OAuthBaseClient.getInstance(clientClass, this);
            client.setRequestIntentFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            client.authorize(uri, this, this);


            //Log.i(TAG, "data scheme: " + uri);

            //client.setRequestIntentFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //client.setRequestIntentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } catch (Exception e) {
            Log.i(TAG, "something went wrong");
        }
    }

    public T getClient() {
        return client;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClientClass() {
        return (Class<T>) GenericsUtil.getTypeArguments(OAuthLoginActivity.class, this.getClass()).get(0);
    }
}
