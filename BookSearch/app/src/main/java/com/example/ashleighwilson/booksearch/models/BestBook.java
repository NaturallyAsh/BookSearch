package com.example.ashleighwilson.booksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public class BestBook {

    public BestBook(@Element(name="author", required=false)
                            Author author,

                            @Element(name="image_url", required=false)
                                    String imageUrl,

                            @Element(name="id", required=false)
                                    String id,

                            @Element(name="title", required=false)
                                    String title) {

        this.author = author;
        this.imageUrl = imageUrl;
        this.id = id;
        this.title = title;
    }

    private String db_id;
    private String db_title;
    private String db_author;
    private String db_image;

    public String getDb_id() {
        return db_id;
    }

    public String getDb_title() {
        return db_title;
    }

    public String getDb_author() {
        return db_author;
    }

    public String getDb_image() {
        return db_image;
    }


    public BestBook(String id, String title, String author, String image) {
        this.db_id = id;
        this.db_title = title;
        this.db_author = author;
        this.db_image = image;
    }


    @Element(name="author", required=false)
    Author author;

    @Element(name="image_url", required=false)
    String imageUrl;

    @Element(name="id", required=false)
    String id;

    @Element(name="title", required=false)
    String title;



    public Author getAuthor() {return this.author;}
    public void setAuthor(Author value) {this.author = value;}

    public String getImageUrl() {return this.imageUrl;}
    public void setImageUrl(String value) {this.imageUrl = value;}

    public String getId() {return this.id;}
    public void setId(String value) {this.id = value;}

    public String getTitle() {return this.title;}
    public void setTitle(String value) {this.title = value;}

    public String getAltBookCover(String identifier) {
        return "https://images.randomhouse.com/cover/" + identifier;
    }


}
