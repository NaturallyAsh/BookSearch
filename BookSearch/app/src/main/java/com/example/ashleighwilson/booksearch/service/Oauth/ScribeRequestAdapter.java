package com.example.ashleighwilson.booksearch.service.Oauth;

import android.net.Uri;

import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.ParameterList;
import com.github.scribejava.core.model.Verb;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;

public class ScribeRequestAdapter extends OAuthRequest {

    private cz.msebera.android.httpclient.client.methods.HttpUriRequest httpUriRequest;
    private HashMap<String, String> oauthParameters;

    public ScribeRequestAdapter(
            cz.msebera.android.httpclient.client.methods.HttpUriRequest httpUriRequest) {

        super(getMethod(httpUriRequest.getMethod()), httpUriRequest.getURI().toString());

        this.httpUriRequest = httpUriRequest;
        this.oauthParameters = new HashMap<String, String>();
    }

    public static Verb getMethod(String method) {
        switch (method) {
            case "GET":
                return Verb.GET;
            case "POST":
                return Verb.POST;
            case "DELETE":
                return Verb.DELETE;
            case "PUT":
                return Verb.PUT;
            case "PATCH":
                return Verb.PATCH;
            case "OPTIONS":
                return Verb.OPTIONS;
        }
        throw new IllegalStateException(method);
    }
    // Adds OAuth parameter with associated value
    @Override
    public void addOAuthParameter(String key, String value) {
        this.oauthParameters.put(key, value);
    }

    // Returns OAuth parameters
    @Override
    public Map<String, String> getOauthParameters() {
        return this.oauthParameters;
    }

    // Adds header entry with associated value
    @Override
    public void addHeader(String key, String value) {
        this.httpUriRequest.addHeader(key, value);
    }

    // Add query string parameters to the HTTP Request.
    @Override
    public void addQuerystringParameter(String key, String value) {
        // Workaround since some OAuth2 require "access_token" and others "oauth_token"
        if (key.equals(OAuthConstants.ACCESS_TOKEN)) { addQuerystringParameter(OAuthConstants.TOKEN, value); }
        // Workaround, convert URI to Uri, build on the URL to add the new query parameter and then update the HTTP Request
        Uri updatedUri = Uri.parse(httpUriRequest.getURI().toString()).buildUpon().appendQueryParameter(key, value).build();
        ((HttpRequestBase) httpUriRequest).setURI(URI.create(updatedUri.toString()));
    }

    // Returns query strings embedded in the URL
    @Override
    public ParameterList getQueryStringParams() {
        try {
            return parseQueryParams();
        } catch (UnsupportedEncodingException e) {
            return new ParameterList();
        }
    }

    // Returns params parsed from the entity body
    @Override
    public ParameterList getBodyParams() {
        if (getVerb() == Verb.GET || getVerb() == Verb.DELETE) { return new ParameterList(); }
        else { return parseEntityParams(); }
    }

    // Returns the full URL with query strings
    @Override
    public String getCompleteUrl() {
        return getHttpRequest().getURI().toString();
    }

    // Returns the base URL without query strings or host
    @Override
    public String getSanitizedUrl() {
        return getCompleteUrl().replaceAll("\\?.*", "").replace("\\:\\d{4}", "");
    }

    // Returns Verb enum for the request method (i.e Verb.GET)
    @Override
    public Verb getVerb() {
        return Verb.valueOf(getHttpRequest().getMethod());
    }

    // Returns simple string representation of the request
    // i.e @Request(GET http://foo.com/bar)
    @Override
    public String toString()
    {
        return String.format("@Request(%s %s)", getVerb(), getCompleteUrl());
    }

    // Returns the underlying HTTP request
    protected HttpUriRequest getHttpRequest() {
        return this.httpUriRequest;
    }

    // Parses and returns the entity provided as a ParameterList
    private ParameterList parseEntityParams() {
        cz.msebera.android.httpclient.HttpEntity entity = null;
        List<NameValuePair> parameters = null;
        try{
            entity = ((HttpEntityEnclosingRequestBase) httpUriRequest).getEntity();
            parameters = new ArrayList<NameValuePair>( URLEncodedUtils.parse(entity));
        } catch (Exception e) {
            return new ParameterList();
        }

        ParameterList list = new ParameterList();
        for (NameValuePair pair : parameters) {
            list.add(pair.getName(), pair.getValue());
        }
        return list;
    }

    // Returns the ParameterList of query parameters parsed from the URL string
    private ParameterList parseQueryParams() throws UnsupportedEncodingException {
        ParameterList params = new ParameterList();
        String queryString = URI.create(getCompleteUrl()).getQuery();
        if (queryString == null) { return params; }
        for (String param : queryString.split("&")) {
            String pair[] = param.split("=");
            String key = URLDecoder.decode(pair[0], "UTF-8");
            String value = "";
            if (pair.length > 1) {
                value = URLDecoder.decode(pair[1], "UTF-8");
            }
            params.add(new String(key), new String(value));
        }
        return params;
    }

    @Override
    public String getRealm() {
        return null;
    }
}
