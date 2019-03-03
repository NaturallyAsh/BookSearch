package com.example.ashleighwilson.booksearch.service.Oauth;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public class OkHttpOAuthProvider extends AbstractOAuthProvider {

    private static final String TAG = OkHttpOAuthProvider.class.getSimpleName();

    public OkHttpOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl, String authorizationWebsiteUrl) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
    }

    @Override
    protected HttpRequest createRequest(String endpointUrl) throws Exception {
        Request request = new Request.Builder()
                .url(endpointUrl)
                .build();

        return new OkHttpRequestAdapter(request);
    }

    @Override
    protected HttpResponse sendRequest(HttpRequest request) throws Exception {
        Request okRequest = ((OkHttpRequestAdapter) request).unwrap();

        Log.d(TAG, "request=" + okRequest);

        OkHttpClient okClient = new OkHttpClient();
        Response response = okClient.newCall(okRequest).execute();

        Log.d(TAG, "response=" + response);

        return new OkHttpResponseAdapter(response);
    }
}
