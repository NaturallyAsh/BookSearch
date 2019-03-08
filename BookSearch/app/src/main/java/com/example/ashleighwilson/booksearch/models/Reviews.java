package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

public class Reviews {
    @Attribute(name="total", required=false)
    Integer total;

    @ElementList(name="review", required=false, entry="review", inline=true)
    List<Review> review;

    @Attribute(name="start", required=false)
    Integer start;

    @Attribute(name="end", required=false)
    Integer end;

    //@Element(name="review", required=false)
    Review review2;

    public Review getReview2() {return this.review2;}

    public Integer getTotal() {return this.total;}
    public void setTotal(Integer value) {this.total = value;}

    public List<Review> getReview() {return this.review;}
    public void setReview(List<Review> value) {this.review = value;}

    public Integer getStart() {return this.start;}
    public void setStart(Integer value) {this.start = value;}

    public Integer getEnd() {return this.end;}
    public void setEnd(Integer value) {this.end = value;}

}
