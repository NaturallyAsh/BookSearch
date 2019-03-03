package com.example.ashleighwilson.booksearch.service.response;

import org.simpleframework.xml.Element;

public class GoodreadsResponse {

    @Element(name = "Request")
    Request request;

    @Override
    public String toString() {
        return "GoodreadsResponse{" +
                "request=" + request +
                '}';
    }
}
