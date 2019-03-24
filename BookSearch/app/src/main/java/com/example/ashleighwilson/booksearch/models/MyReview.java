package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Element;

import retrofit2.http.Url;

public class MyReview {
    @Element(name="recommended_for", required=false)
    String recommendedFor;

    @Element(name="date_updated", required=false)
    String dateUpdated;

    @Element(name="spoiler_flag", required=false)
    String spoilerFlag;

    @Element(name="rating", required=false)
    String rating;

    @Element(name="body", required=false)
    String body;

    @Element(name="shelves", required=false)
    Shelves shelves;

    @Element(name="date_added", required=false)
    String dateAdded;

    @Element(name="comments_count", required=false)
    String commentsCount;

    @Element(name="owned", required=false)
    String owned;

    @Element(name="started_at", required=false)
    String startedAt;

    @Element(name="read_at", required=false)
    String readAt;

    @Element(name="votes", required=false)
    String votes;

    @Element(name="recommended_by", required=false)
    String recommendedBy;

    @Element(name="id", required=false)
    String id;

    @Element(name="spoilers_state", required=false)
    String spoilersState;

    @Element(name="read_count", required=false)
    String readCount;

    public String getRecommendedFor() {return this.recommendedFor;}
    public void setRecommendedFor(String value) {this.recommendedFor = value;}

    public String getDateUpdated() {return this.dateUpdated;}
    public void setDateUpdated(String value) {this.dateUpdated = value;}

    public String getSpoilerFlag() {return this.spoilerFlag;}
    public void setSpoilerFlag(String value) {this.spoilerFlag = value;}

    public String getRating() {return this.rating;}
    public void setRating(String value) {this.rating = value;}

    public String getBody() {return this.body;}
    public void setBody(String value) {this.body = value;}

    public Shelves getShelves() {return this.shelves;}
    public void setShelves(Shelves value) {this.shelves = value;}

    public String getDateAdded() {return this.dateAdded;}
    public void setDateAdded(String value) {this.dateAdded = value;}

    public String getCommentsCount() {return this.commentsCount;}
    public void setCommentsCount(String value) {this.commentsCount = value;}

    public String getOwned() {return this.owned;}
    public void setOwned(String value) {this.owned = value;}

    public String getStartedAt() {return this.startedAt;}
    public void setStartedAt(String value) {this.startedAt = value;}

    public String getReadAt() {return this.readAt;}
    public void setReadAt(String value) {this.readAt = value;}

    public String getVotes() {return this.votes;}
    public void setVotes(String value) {this.votes = value;}

    public String getRecommendedBy() {return this.recommendedBy;}
    public void setRecommendedBy(String value) {this.recommendedBy = value;}

    public String getId() {return this.id;}
    public void setId(String value) {this.id = value;}

    public String getSpoilersState() {return this.spoilersState;}
    public void setSpoilersState(String value) {this.spoilersState = value;}

    public String getReadCount() {return this.readCount;}
    public void setReadCount(String value) {this.readCount = value;}
}
