package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.Reviews;

import org.simpleframework.xml.Element;

public class ReviewListResponse extends GoodreadsResponse{

    @Element(name = "reviews", required = false)
    Reviews reviews;

    @Override
    public String toString() {
        return "ReviewListResponse{" +
                "review=" + reviews +
                '}';
    }

    Reviews getReview() {
        return reviews;
    }
}
