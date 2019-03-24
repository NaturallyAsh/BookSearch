package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Text;

import java.util.List;

public class SeriesWorks {
    //@Text(required=false)
    String textValue;

    //@ElementList(name="series_work", required=false, inline = true)
    //List<SeriesWork> seriesWork;
    @Element(name = "series_work", required = false)
    SeriesWork seriesWork;

    public String getTextValue() {return this.textValue;}
    public void setTextValue(String value) {this.textValue = value;}

    //public List<SeriesWork> getSeriesWork() {return this.seriesWork;}
    //public void setSeriesWork(List<SeriesWork> value) {this.seriesWork = value;}

    public SeriesWork getSeriesWork() {
        return this.seriesWork;
    }
}
