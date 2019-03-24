package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by alek on 16/02/15.
 */
public class Shelves {

    @ElementList(name="shelf", required=false, inline = true)
    List<Shelf> shelf;

    public List<Shelf> getShelf() {return this.shelf;}
    public void setShelf(List<Shelf> value) {this.shelf = value;}

}
