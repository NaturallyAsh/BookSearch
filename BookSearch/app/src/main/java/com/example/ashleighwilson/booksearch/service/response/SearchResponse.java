package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.Search;

import org.simpleframework.xml.Element;

public class SearchResponse extends GoodreadsResponse {
    @Element(name="search", required=false)
    public Search search;

    public Search getSearch() {return this.search;}
    public void setSearch(Search value) {this.search = value;}
}
