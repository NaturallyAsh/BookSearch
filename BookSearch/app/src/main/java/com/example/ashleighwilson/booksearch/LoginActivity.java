package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.ashleighwilson.booksearch.service.GoodreadsClient;
import com.example.ashleighwilson.booksearch.service.OAuthLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends OAuthLoginActivity<GoodreadsClient> {

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

        /*webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });*/
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBT.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
                getClient().connect();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Log.i(TAG, "success");
    }

    @Override
    public void onLoginFailure(Exception e) {
        Log.i(TAG, "failure");
    }
}
