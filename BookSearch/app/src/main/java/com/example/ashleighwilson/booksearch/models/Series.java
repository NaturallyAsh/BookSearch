package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Series {
    @Element(name="note", required=false)
    String note;

    @Element(name="series_works_count", required=false)
    String seriesWorksCount;

    @ElementList(name="series_works", required=false)
    List<SeriesWork> seriesWorks;

    @Element(name="numbered", required=false)
    String numbered;

    @Element(name="description", required=false)
    String description;

    @Element(name="id", required=false)
    String id;

    @Element(name="title", required=false)
    String title;

    @Element(name="primary_work_count", required=false)
    String primaryWorkCount;

    public String getNote() {return this.note;}
    public void setNote(String value) {this.note = value;}

    public String getSeriesWorksCount() {return this.seriesWorksCount;}
    public void setSeriesWorksCount(String value) {this.seriesWorksCount = value;}

    public List<SeriesWork> getSeriesWorks() {return this.seriesWorks;}
    public void setSeriesWorks(List<SeriesWork> value) {this.seriesWorks = value;}

    public String getNumbered() {return this.numbered;}
    public void setNumbered(String value) {this.numbered = value;}

    public String getDescription() {return this.description;}
    public void setDescription(String value) {this.description = value;}

    public String getId() {return this.id;}
    public void setId(String value) {this.id = value;}

    public String getTitle() {return this.title;}
    public void setTitle(String value) {this.title = value;}

    public String getPrimaryWorkCount() {return this.primaryWorkCount;}
    public void setPrimaryWorkCount(String value) {this.primaryWorkCount = value;}
}
