package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.ashleighwilson.booksearch.service.GoodreadsClient;
import com.example.ashleighwilson.booksearch.service.OAuthLoginActivity;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthAsyncHttpClient;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthBaseClient;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import oauth.signpost.OAuth;

public class LoginActivity extends OAuthLoginActivity<GoodreadsClient>
    implements OAuthBaseClient.OAuthAccessHandler {

    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final String CLIENT_KEY = BuildConfig.Goodreads_Api_Key;
    public static final String CLIENT_SECRET = BuildConfig.Goodreads_Secret;
    public static final String OAUTH_REQUEST_TOKEN = "/oauth/request_token";
    public static final String OAUTH_ACCESS_TOKEN = "/oauth/access_token";
    public static final String OAUTH_AUTHORIZE = "/oauth/authorize?mobile=1";

    @BindView(R.id.login_Button)
    Button loginBT;
    @BindView(R.id.login_progressContainer)
    View progressContainer;
    @BindView(R.id.login_webView)
    WebView webView;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                processUrl(url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                progressContainer.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBT.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
                getClient().connect();
                Log.i(TAG, "connection: " + getClient().toString());
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Log.i(TAG, "success");
        progressContainer.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(Exception e) {
        Log.i(TAG, "failure");
    }

    @Override
    public void AuthUrl(String url) {
        //Log.i(TAG, "url: " + url);
        webView.loadUrl(url);
    }

    private synchronized void processUrl(String mUrl) {
        Uri uri = Uri.parse(mUrl);

        String oauth_token = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
        String authorize = uri.getQueryParameter("authorize");

        if (authorize.equals("1")) {
            //Log.i(TAG, "authorize equals: " + authorize);

            getClient().authorize(uri, this, this);
        }
    }
}
