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
public class Author {

    /*public Author(

    @Element(name = "id", required = false)
    long id,

    @Element(name = "name", required = false)
    String name,

    @Element(name = "link", required = false)
    String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }*/

    @Element(name = "id", required = false)
    long id;

    @Element(name = "name", required = false)
    String name;

    @Element(name = "link", required = false)
    String link;

    public String getName() {
        return name;
    }
}
