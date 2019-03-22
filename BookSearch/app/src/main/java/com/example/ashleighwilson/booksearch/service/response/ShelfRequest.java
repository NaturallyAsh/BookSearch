package com.example.ashleighwilson.booksearch.service.response;

import com.example.ashleighwilson.booksearch.models.Id;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.stream.Position;

@Root(name = "shelf", strict = false)
public class ShelfRequest {

    @Element(name="review-id", required=false)
    String reviewId;

    @Element(name="updated-at", required=false)
    String updatedAt;

    @Element(name="name", required=false)
    String name;

    @Element(name="exclusive", required=false)
    String exclusive;

    @Element(name="created-at", required=false)
    String createdAt;

    @Element(name="id", required=false)
    Id id;

    @Element(name="position", required=false)
    String position;

    @Element(name="user-shelf-id", required=false)
    String userShelfId;

    public String getReviewId() {return this.reviewId;}
    public void setReviewId(String value) {this.reviewId = value;}

    public String getUpdatedAt() {return this.updatedAt;}
    public void setUpdatedAt(String value) {this.updatedAt = value;}

    public String getName() {return this.name;}
    public void setName(String value) {this.name = value;}

    public String getExclusive() {return this.exclusive;}
    public void setExclusive(String value) {this.exclusive = value;}

    public String getCreatedAt() {return this.createdAt;}
    public void setCreatedAt(String value) {this.createdAt = value;}

    public Id getId() {return this.id;}
    public void setId(Id value) {this.id = value;}

    public String getPosition() {return this.position;}
    public void setPosition(String value) {this.position = value;}

    public String getUserShelfId() {return this.userShelfId;}
    public void setUserShelfId(String value) {this.userShelfId = value;}
}
