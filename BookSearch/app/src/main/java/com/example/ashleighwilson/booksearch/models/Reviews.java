package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Reviews {
    @Attribute(name = "total", required = false)
    Integer total;

    @ElementList(name="review", required=false, entry="review", inline=true)
    List<Review> review;

    @Attribute(name="start", required=false)
    Integer start;

    @Attribute(name="end", required=false)
    Integer end;
}
