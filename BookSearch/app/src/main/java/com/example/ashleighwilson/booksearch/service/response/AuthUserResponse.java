package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.AuthUser;

import org.simpleframework.xml.Element;

public class AuthUserResponse extends GoodreadsResponse {
    @Element(name = "user")
    AuthUser user;

    @Override
    public String toString() {
        return "AuthUserResponse{" +
                "request=" + request +
                ", user=" + user +
                '}';
    }

    public AuthUser getUser() {
        return user;
    }
}
