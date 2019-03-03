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

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthAccessTokenRequest;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthRequestTokenRequest;
import com.example.ashleighwilson.booksearch.service.Oauth.OAuthSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    OAuthConsumer oAuthConsumer;
    boolean callbackProcessed;
    private SpiceManager spiceManager = new SpiceManager(OAuthSpiceService.class);

    @BindView(R.id.login_Button)
    Button loginBT;
    @BindView(R.id.login_progressContainer)
    View progressContainer;
    @BindView(R.id.login_webView)
    WebView webView;

    @Inject
    PreferenceUser preferenceUser;

    @Inject
    AuthUser currentUser;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        ButterKnife.bind(this);

        Injector.getInstance().inject(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                processOnPageStarted(url);
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
                processLoginButton();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void processLoginButton() {
        OAuthRequestTokenRequest requestTokenRequest = new OAuthRequestTokenRequest();
        spiceManager.execute(requestTokenRequest, new OAuthRequestTokenListener());
    }

    private synchronized void processOnPageStarted(String url) {
        if (!url.startsWith(BuildConfig.Goodreads_Base_Url)) {
            Log.i(TAG, "url starts with: " + BuildConfig.Goodreads_Base_Url);
            return;
        }
        if (callbackProcessed) {
            Log.i(TAG, "callback processed");
            //return;
        }

        callbackProcessed = true;

        Uri uri = Uri.parse(url);

        String oauth_token = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
        String authorize = uri.getQueryParameter("authorize");
        Log.i(TAG, "authorize: " + authorize);

        Log.i(TAG, "url: " + url);
        if (authorize != null) {
            if (authorize.equals("1")) {
                OAuthAccessTokenRequest accessTokenRequest = new OAuthAccessTokenRequest(oauth_token);
                spiceManager.execute(accessTokenRequest, new OAuthAccessTokenListener());
            }
        } else {
            Log.i(TAG, "authorize is null");
        }
    }

    private class OAuthRequestTokenListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            spiceException.printStackTrace();
        }

        @Override
        public void onRequestSuccess(String authorizeUrl) {
            webView.loadUrl(authorizeUrl);
        }
    }

    private class OAuthAccessTokenListener implements RequestListener<AuthUser> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            spiceException.printStackTrace();
        }

        @Override
        public void onRequestSuccess(AuthUser authUser) {
            if (authUser == null) {
                Log.i(TAG, "Authorization error. User null");
                return;
            }
            progressContainer.setVisibility(View.GONE);
            Log.i(TAG, "user image: " + authUser.getImage_url());
            preferenceUser.setCurrentUser(authUser);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
