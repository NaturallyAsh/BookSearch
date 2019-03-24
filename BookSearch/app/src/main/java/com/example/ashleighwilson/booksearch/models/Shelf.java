package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Shelf {

    @Attribute(name="review_shelf_id", required=false)
    String reviewShelfId;

    @Attribute(name="name", required=false)
    String name;

    @Attribute(name="exclusive", required=false)
    Boolean exclusive;

    @Attribute(name="id", required=false)
    Integer id;

    @Attribute(name="sortable", required=false)
    Boolean sortable;

    public String getReviewShelfId() {return this.reviewShelfId;}
    public void setReviewShelfId(String value) {this.reviewShelfId = value;}

    public String getName() {return this.name;}
    public void setName(String value) {this.name = value;}

    public Boolean getExclusive() {return this.exclusive;}
    public void setExclusive(Boolean value) {this.exclusive = value;}

    public Integer getId() {return this.id;}
    public void setId(Integer value) {this.id = value;}

    public Boolean getSortable() {return this.sortable;}
    public void setSortable(Boolean value) {this.sortable = value;}
}
