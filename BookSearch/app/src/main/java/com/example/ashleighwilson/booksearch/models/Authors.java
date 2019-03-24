package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

public class Authors {

    //needed to make into an elementlist and do inline true
    //to avoid duplicate persistence exception
    @ElementList(name="author", required=false, inline = true)
    List<Author> author;

    public List<Author> getAuthor() {return this.author;}
    public void setAuthor(List<Author> value) {this.author = value;}
}
