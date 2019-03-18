package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

import java.text.NumberFormat;

public class Work {
    @Element(required = false)
    long best_book_id;

    @Element(required = false)
    long books_count;

    @Element(required = false)
    long default_chaptering_book_id;

    @Element(required = false)
    String default_description_language_code;

    @Element(required = false)
    long desc_user_id;

    @Element(required = false)
    long id;

    @Element(required = false)
    String media_type;

    @Element(required = false)
    long original_language_id;

    @Element(required = false)
    long original_publication_day;

    @Element(required = false)
    long original_publication_month;

    @Element(required = false)
    long original_publication_year;

    @Element(required = false)
    String original_title;

    @Element(required = false)
    String rating_dist;

    @Element(required = false)
    long ratings_count;

    @Element(required = false)
    long ratings_sum;

    @Element(required = false)
    long reviews_count;

    @Element(required = false)
    long text_reviews_count;

    @Element(name="best_book", required=false)
    BestBook bestBook;

    public BestBook getBestBook() {return this.bestBook;}
    public void setBestBook(BestBook value) {this.bestBook = value;}

    public long getText_reviews_count() {
        return text_reviews_count;
    }

    public long getRatingsCount() {
        return ratings_count;
    }

    public long getReviewsCount() {
        return reviews_count;
    }

    public String getRatingsCountFormatted() {
        return NumberFormat.getInstance().format(ratings_count);
    }

    public String getRatingDist() {return this.rating_dist;}
    public void setRatingDist(String value) {this.rating_dist = value;}
}
