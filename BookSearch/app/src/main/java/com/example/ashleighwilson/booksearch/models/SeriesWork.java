package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

public class SeriesWork {
    @Element(name="work", required=false)
    Work work;

    @Element(name="series", required=false)
    Series series;

    @Element(name="user_position", required=false)
    String userPosition;

    @Element(name="id", required=false)
    String id;

    @Element(name = "best_book", required = false)
    BestBook bestBook;

    public BestBook getBestBook() {
        return bestBook;
    }

    public void setBestBook(BestBook bestBook) {
        this.bestBook = bestBook;
    }

    public Work getWork() {return this.work;}
    public void setWork(Work value) {this.work = value;}

    public String getUserPosition() {return this.userPosition;}
    public void setUserPosition(String value) {this.userPosition = value;}

    public String getId() {return this.id;}
    public void setId(String value) {this.id = value;}

    public Series getSeries() {return this.series;}
    public void setSeries(Series value) {this.series = value;}
}
