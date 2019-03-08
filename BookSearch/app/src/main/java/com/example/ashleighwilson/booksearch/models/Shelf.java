package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Shelf {
    /*@Element
    long book_count;

    @Element(required = false)
    String description;

    @Element(required = false)
    String display_fields;

    @Element
    boolean exclusive_flag;

    @Element
    boolean featured;

    @Element
    long id;

    @Element
    String name;

    @Element(required = false)
    String order;

    @Element(required = false)
    long per_page;

    @Element(required = false)
    boolean recommend_for;

    @Element(required = false)
    String sort;

    @Element(required = false)
    boolean sticky;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "book_count=" + book_count +
                ", description='" + description + '\'' +
                ", display_fields='" + display_fields + '\'' +
                ", exclusive_flag=" + exclusive_flag +
                ", featured=" + featured +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order='" + order + '\'' +
                ", per_page=" + per_page +
                ", recommend_for=" + recommend_for +
                ", sort='" + sort + '\'' +
                ", sticky=" + sticky +
                '}';
    }*/
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
