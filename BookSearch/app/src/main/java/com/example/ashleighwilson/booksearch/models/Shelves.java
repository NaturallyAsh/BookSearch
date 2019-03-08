package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by alek on 16/02/15.
 */
public class Shelves {

    @Element(name="shelf", required=false)
    Shelf shelf;

    public Shelf getShelf() {return this.shelf;}
    public void setShelf(Shelf value) {this.shelf = value;}

}
