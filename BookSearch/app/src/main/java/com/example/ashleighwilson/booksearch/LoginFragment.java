package com.example.ashleighwilson.booksearch;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

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
    /*
    OAuth workflow
    /oauth/request_token
    /oauth/authorize?oauth_token=foo&oauth_callback=http://my.callback.com
    /oauth/access_token?oauth_token=foo
     */

    public LoginFragment() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, rootView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBT.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }
}
