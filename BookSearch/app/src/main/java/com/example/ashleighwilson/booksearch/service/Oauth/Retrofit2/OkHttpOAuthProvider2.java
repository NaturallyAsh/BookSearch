package com.example.ashleighwilson.booksearch.service.Oauth.Retrofit2;

import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpRequestAdapter;
import com.example.ashleighwilson.booksearch.service.Oauth.OkHttpResponseAdapter;

import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpOAuthProvider2 extends AbstractOAuthProvider {

    private transient OkHttpClient okHttpClient;

    /**
     * Constructs a new {@code OkHttpOAuthProvider} with a default {@link OkHttpClient}.
     */
    public OkHttpOAuthProvider2(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
                               String authorizationWebsiteUrl) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * Constructs a new {@code OkHttpOAuthProvider}.
     * @param okHttpClient the OkHttpClient to use for sending requests.
     */
    public OkHttpOAuthProvider2(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
                               String authorizationWebsiteUrl, OkHttpClient okHttpClient) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.okHttpClient = okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpRequest createRequest(String endpointUrl) throws Exception {
        Request request = new Request.Builder().url(endpointUrl).build();
        return new OkHttpRequestAdapter2(request);
    }

    @Override
    protected HttpResponse sendRequest(HttpRequest request) throws Exception {
        Response response = okHttpClient.newCall((Request) request.unwrap()).execute();
        return new OkHttpResponseAdapter2(response);
    }

    @Override
    protected void closeConnection(HttpRequest request, HttpResponse response) throws Exception {
        if (response != null) {
            Response r = (Response) response.unwrap();
            r.close();
        }
    }
}
