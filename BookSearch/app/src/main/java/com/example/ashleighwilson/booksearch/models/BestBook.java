package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

public class BestBook {
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
