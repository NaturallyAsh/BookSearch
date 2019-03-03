package com.example.ashleighwilson.booksearch.service.Oauth;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class RetrofitSigningOkClient extends OkClient {

    private final RetrofitHttpOAuthConsumer mOAuthConsumer;

    public RetrofitSigningOkClient(RetrofitHttpOAuthConsumer consumer) {
        mOAuthConsumer = consumer;
    }

//    public RetrofitSigningOkClient(OkHttpClient client, RetrofitHttpOAuthConsumer consumer) {
//        super(client);
//        mOAuthConsumer = consumer;
//    }

    @Override
    public Response execute(Request request) {
        Request requestToSend = request;

        try {
            RetrofitHttpRequestAdapter signedAdapter = (RetrofitHttpRequestAdapter) mOAuthConsumer.sign(request);
            requestToSend = (Request) signedAdapter.unwrap();
            return super.execute(requestToSend);
        } catch (OAuthMessageSignerException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            // Fail to sign, ignore
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
