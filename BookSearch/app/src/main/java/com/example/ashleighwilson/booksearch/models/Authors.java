package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

public class Authors {

    @Element(name="author", required=false)
    Author author;

    public Author getAuthor() {return this.author;}
    public void setAuthor(Author value) {this.author = value;}
}
