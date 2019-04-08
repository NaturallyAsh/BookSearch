package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Search {
    @Element(name="results-end", required=false)
    String resultsEnd;

    @Element(name="total-results", required=false)
    String totalResults;

    @Element(name="query", required=false)
    String query;

    @Element(name="source", required=false)
    String source;

    @Element(name="query-time-seconds", required=false)
    String queryTimeSeconds;

    @ElementList(name="results", required=false)
    List<Work> results;

    @Element(name="results-start", required=false)
    String resultsStart;

    public String getResultsEnd() {return this.resultsEnd;}
    public void setResultsEnd(String value) {this.resultsEnd = value;}

    public String getTotalResults() {return this.totalResults;}
    public void setTotalResults(String value) {this.totalResults = value;}

    public String getQuery() {return this.query;}
    public void setQuery(String value) {this.query = value;}

    public String getSource() {return this.source;}
    public void setSource(String value) {this.source = value;}

    public String getQueryTimeSeconds() {return this.queryTimeSeconds;}
    public void setQueryTimeSeconds(String value) {this.queryTimeSeconds = value;}

    public List<Work> getResults() {return this.results;}
    public void setResults(List<Work> value) {this.results = value;}

    public String getResultsStart() {return this.resultsStart;}
    public void setResultsStart(String value) {this.resultsStart = value;}
}
