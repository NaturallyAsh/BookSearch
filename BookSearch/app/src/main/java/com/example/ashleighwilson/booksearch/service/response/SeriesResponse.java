package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.Series;

import org.simpleframework.xml.Element;

public class SeriesResponse extends GoodreadsResponse{
    @Element(name="series", required=false)
    Series series;

    public Series getSeries() {return this.series;}
    public void setSeries(Series value) {this.series = value;}
}
