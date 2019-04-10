package com.example.ashleighwilson.booksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "series_work")
public class SeriesWork implements Parcelable {

    public SeriesWork(@Element(name="work", required=false)
                              Work work,

                              @Element(name="series", required=false)
                                      Series series,

                              @Element(name="user_position", required=false)
                                      String userPosition,

                              @Element(name="id", required=false)
                                      String id,

                              @ElementList(name = "best_book", required = false, inline = true)
                              List<BestBook> bestBook) {

        this.work = work;
        this.series = series;
        this.userPosition = userPosition;
        this.id = id;
        this.bestBook = bestBook;
    }
    @Element(name="work", required=false)
    Work work;

    @Element(name="series", required=false)
    Series series;

    @Element(name="user_position", required=false)
    String userPosition;

    @Element(name="id", required=false)
    String id;

    @ElementList(name = "best_book", required = false, inline = true)
    List<BestBook> bestBook;

    protected SeriesWork(Parcel in) {
        userPosition = in.readString();
        id = in.readString();
    }

    public static final Creator<SeriesWork> CREATOR = new Creator<SeriesWork>() {
        @Override
        public SeriesWork createFromParcel(Parcel in) {
            return new SeriesWork(in);
        }

        @Override
        public SeriesWork[] newArray(int size) {
            return new SeriesWork[size];
        }
    };

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SeriesWork fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SeriesWork.class);
    }

    public List<BestBook> getBestBook() {
        return bestBook;
    }

    public void setBestBook(List<BestBook> bestBook) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userPosition);
        dest.writeString(id);
    }
}
