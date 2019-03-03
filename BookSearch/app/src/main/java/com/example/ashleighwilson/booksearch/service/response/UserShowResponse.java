package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.AuthUser;

import org.simpleframework.xml.Element;

public class UserShowResponse extends GoodreadsResponse {

    @Element(name = "user")
    AuthUser user;

    @Override
    public String toString() {
        return "UserShowResponse{" +
                "user=" + user +
                '}';
    }
    public AuthUser getUser() {
        return user;
    }
}
