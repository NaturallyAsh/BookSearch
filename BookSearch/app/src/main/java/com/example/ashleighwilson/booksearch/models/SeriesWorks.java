package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;

public class SeriesWorks {
    //@Text(required=false)
    //String textValue;

    @Element(name="series_work", required=false)
    SeriesWork seriesWork;

    //public String getTextValue() {return this.textValue;}
    //public void setTextValue(String value) {this.textValue = value;}

    public SeriesWork getSeriesWork() {return this.seriesWork;}
    public void setSeriesWork(SeriesWork value) {this.seriesWork = value;}
}
