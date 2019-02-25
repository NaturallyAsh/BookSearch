package com.example.ashleighwilson.booksearch.service.Oauth;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.codepath.utils.AsyncSimpleTask;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

public class OAuthAsyncHttpClient extends AsyncHttpClient {

    private static final String TAG = OAuthAsyncHttpClient.class.getSimpleName();

    private BaseApi apiInstance;
    private OAuthTokenHandler handler;
    private OAuth1AccessToken accessToken;
    private OAuth10aService service;

    // Requires the apiClass, consumerKey, consumerSecret and callbackUrl along with the TokenHandler
    public OAuthAsyncHttpClient(BaseApi apiInstance, String consumerKey, String consumerSecret, String callbackUrl,
                                OAuthTokenHandler handler) {
        this.apiInstance = apiInstance;
        this.handler = handler;
        if (callbackUrl == null) { callbackUrl = OAuthConstants.OOB; }
        this.service = (OAuth10aService) new ServiceBuilder(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl)
                .build(apiInstance);
    }

    // Get a request token and the authorization url
    // Once fetched, fire the onReceivedRequestToken for the request token handler
    // Works for both OAuth1.0a and OAuth2
    public void fetchRequestToken() {
        new AsyncSimpleTask(new AsyncSimpleTask.AsyncSimpleTaskHandler() {
            String authorizeUrl = null;
            Exception e = null;
            OAuth1RequestToken requestToken;

            public void doInBackground() {
                try {
                    if (service.getVersion() == "1.0") {
                        OAuth10aService oAuth10aService = (OAuth10aService) service;
                        requestToken = oAuth10aService.getRequestToken();
                        authorizeUrl = oAuth10aService.getAuthorizationUrl((OAuth1RequestToken) requestToken);
                        Log.i(TAG, "OAuth 1 is the version");

                    } else if (service.getVersion() == "2.0") {
                        //OAuth20Service oAuth20Service = (OAuth20Service) service;
                        //authorizeUrl = oAuth20Service.getAuthorizationUrl((PKCE) null);
                        Log.i(TAG, "OAuth 2 is the version");
                    }
                } catch (Exception e) {
                    this.e = e;
                }
            }

            public void onPostExecute() {
                if (e != null) {
                    handler.onFailure(e);
                    Log.i(TAG, "handler on failure");
                } else {
                    handler.onReceivedRequestToken(requestToken, authorizeUrl, service.getVersion());
                    Log.i(TAG, "handler on received request token");

                }
            }
        });
    }

    // Get the access token by exchanging the requestToken to the defined URL
    // Once receiving the access token, fires the onReceivedAccessToken method on the handler
    public void fetchAccessToken(final OAuth1RequestToken requestToken, final Uri uri) {

        new AsyncSimpleTask(new AsyncSimpleTask.AsyncSimpleTaskHandler() {
            Exception e = null;

            public void doInBackground() {
                // Fetch the verifier code from redirect url parameters
                Uri authorizedUri = uri;

                try {
                    if (service.getVersion() == "1.0") {
                        // Use verifier token to fetch access token

                        if (authorizedUri.getQuery().contains(OAuthConstants.VERIFIER)) {
                            String oauth_verifier = authorizedUri.getQueryParameter(OAuthConstants.VERIFIER);
                            OAuth1RequestToken oAuth1RequestToken = (OAuth1RequestToken) requestToken;
                            OAuth10aService oAuth10aService = (OAuth10aService) service;
                            accessToken = oAuth10aService.getAccessToken(oAuth1RequestToken, oauth_verifier);
                        }
                        else { // verifier was null
                            throw new OAuthException("No verifier code was returned with uri '" + uri + "' " +
                                    "and access token cannot be retrieved");
                        }
                    } else if (service.getVersion() == "2.0") {
                        if (authorizedUri.getQuery().contains(OAuthConstants.CODE)) {
                            String code = authorizedUri.getQueryParameter(OAuthConstants.CODE);
                            //OAuth20Service oAuth20Service = (OAuth20Service) service;
                            //accessToken = oAuth20Service.getAccessToken(code);
                            Log.i(TAG, "OAuth 2 is the version");

                        }
                        else { // verifier was null
                            throw new OAuthException("No code was returned with uri '" + uri + "' " +
                                    "and access token cannot be retrieved");
                        }
                    }
                } catch (Exception e) {
                    this.e = e;
                }
            }

            public void onPostExecute() {
                if (e != null) {
                    handler.onFailure(e);
                } else {
                    setAccessToken(accessToken);
                    handler.onReceivedAccessToken(accessToken, service.getVersion());
                }
            }
        });
    }

    // Set the access token used for signing requests
    public void setAccessToken(OAuth1AccessToken accessToken) {
        if (accessToken == null) {
            this.accessToken = null;
        } else {
            this.accessToken = accessToken;
        }
    }

    public OAuth1AccessToken getAccessToken() {
        return this.accessToken;
    }

    // Send scribe signed request based on the async http client to construct a signed request
    // Accepts an HttpEntity which has the underlying entity for the request params

    @Override
    protected RequestHandle sendRequest(
            cz.msebera.android.httpclient.impl.client.DefaultHttpClient client,
            cz.msebera.android.httpclient.protocol.HttpContext httpContext,
            cz.msebera.android.httpclient.client.methods.HttpUriRequest uriRequest,
            String contentType, ResponseHandlerInterface responseHandler,
            Context context) {

        if (this.service != null && accessToken != null) {
            try {
                ScribeRequestAdapter adapter = new ScribeRequestAdapter(uriRequest);
                this.service.signRequest(accessToken, adapter);
                return super.sendRequest(client, httpContext, uriRequest, contentType, responseHandler, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (accessToken == null) {
            throw new OAuthException("Cannot send unauthenticated requests for " + apiInstance.getClass().getSimpleName() + " client. Please attach an access token!");
        } else { // service is null
            throw new OAuthException("Cannot send unauthenticated requests for undefined service. Please specify a valid api service!");
        }
        return null; // Hopefully never reaches here
    }

    // Defines the interface handler for different token handlers
    public interface OAuthTokenHandler {
        public void onReceivedRequestToken(OAuth1RequestToken requestToken, String authorizeUrl, String oAuthVersion);
        public void onReceivedAccessToken(OAuth1AccessToken accessToken, String oAuthVersion);
        public void onFailure(Exception e);
    }
}
