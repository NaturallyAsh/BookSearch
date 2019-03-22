package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.Reviews;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "GoodreadsResponse")
public class GoodreadsResponse {

    @Element(name = "Request")
    Request request;

    //@Element(name="reviews", required=false)
    //Reviews reviews;

    //public Reviews getReviews() {return this.reviews;}
    //public void setReviews(Reviews value) {this.reviews = value;}

    public Request getRequest() {return this.request;}
    public void setRequest(Request value) {this.request = value;}

    @Override
    public String toString() {
        return "GoodreadsResponse{" +
                "request=" + request +
                '}';
    }
}
