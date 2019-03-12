package com.example.ashleighwilson.booksearch.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alek on 30/12/14.
 */
@Root(name = "author", strict = false)
public class Author implements Parcelable{

    public Author(

    @Element(name = "id", required = false)
    long id,

    @Element(name = "name", required = false)
    String name,

    @Element(name = "link", required = false)
    String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    @Element(name = "id", required = false)
    long id;

    @Element(name = "name", required = false)
    String name;

    @Element(name = "link", required = false)
    String link;


    protected Author(Parcel in) {
        id = in.readLong();
        name = in.readString();
        link = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(link);
    }
}
