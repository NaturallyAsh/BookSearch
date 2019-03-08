package com.example.ashleighwilson.booksearch.models;

import com.google.gson.Gson;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by alek on 30/12/14.
 */
public class Review {

    @Element(name="recommended_for", required=false)
    String recommendedFor;

    @Element(name="date_updated", required=false)
    String dateUpdated;

    @Element(name="spoiler_flag", required=false)
    String spoilerFlag;

    @Element(name="book", required=false)
    UserBook book;

    @Element(name="rating", required=false)
    String rating;

    //@Element(name="link", required=false)
    //Link link;

    @Element(name="body", required=false)
    String body;

    @Element(name="shelves", required=false)
    Shelves shelves;

    //@Element(name="url", required=false)
    //Url url;

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

    @Element(required = false)
    AuthUser user;

    public AuthUser getUser() {
        return user;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Review fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Review.class);
    }


    public String getRecommendedFor() {return this.recommendedFor;}
    public void setRecommendedFor(String value) {this.recommendedFor = value;}

    public String getDateUpdated() {return this.dateUpdated;}
    public void setDateUpdated(String value) {this.dateUpdated = value;}

    public String getSpoilerFlag() {return this.spoilerFlag;}
    public void setSpoilerFlag(String value) {this.spoilerFlag = value;}

    public UserBook getBook() {return this.book;}
    public void setBook(UserBook value) {this.book = value;}

    public String getRating() {return this.rating;}
    public void setRating(String value) {this.rating = value;}

    //public Link getLink() {return this.link;}
    //public void setLink(Link value) {this.link = value;}

    public String getBody() {return this.body;}
    public void setBody(String value) {this.body = value;}

    public Shelves getShelves() {return this.shelves;}
    public void setShelves(Shelves value) {this.shelves = value;}

    //public Url getUrl() {return this.url;}
    //public void setUrl(Url value) {this.url = value;}

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

    /*@Element(name = "book-id", required = false)
    long bookId;

    @Element(name = "comments-count", required = false)
    long commentsCount;

    @Element(name = "created-at", required = false)
    String createdAt;

    @Element(required = false)
    String created_at;

    @Element(name = "encrypted_notes", required = false)
    String encrypted_notes;

    @Element(name = "hidden-flag", required = false)
    boolean hiddenFlag;

    @Element(required = false)
    boolean hidden_flag;

    @Element(required = false)
    long id;

    @Element(name = "language-code", required = false)
    long languageCode;

    @Element(required = false)
    long language_code;

    @Element(name = "last-comment-at", required = false)
    String lastCommentAt;

    @Element(required = false)
    String last_comment_at;

    @Element(name = "last-revision-at", required = false)
    String lastRevisionAt;

    @Element(required = false)
    String last_revision_at;

    @Element(name = "non-friends-rating-count", required = false)
    long nonFriendsRatingCount;

    @Element(required = false)
    long non_friends_rating_count;

    @Element(required = false)
    String notes;

    @Element(required = false)
    long rating;

    @Element(name = "ratings-count", required = false)
    long ratingsCount;

    @Element(required = false)
    long ratings_count;

    @Element(name = "ratings-sum", required = false)
    long ratingsSum;

    @Element(required = false)
    long ratings_sum;

    @Element(name = "read-at", required = false)
    String readAt;

    @Element(name = "read-count", required = false)
    String readCount;

    @Element(name = "read-status", required = false)
    String readStatus;

    @Element(required = false)
    String read_status;

    @Element(required = false)
    String recommendation;

    @Element(required = false)
    long recommender_user_id1;

    @Element(name = "recommender-user-id1", required = false)
    long recommenderUserId;

    @Element(required = false)
    String recommender_user_name1;

    @Element(name = "recommender-user-name1", required = false)
    String recommenderUserName;

    @Element(required = false)
    String review;

    @Element(name = "sell-flag", required = false)
    boolean sellFlag;

    @Element(required = false)
    boolean sell_flag;

    @Element(name = "spoiler-flag", required = false)
    boolean spoilerFlag;

    @Element(name = "started-at", required = false)
    String startedAt;

    @Element(name = "updated-at", required = false)
    String updatedAt;

    @Element(required = false)
    String updated_at;

    @Element(name = "user-id", required = false)
    long userId;

    @Element(required = false)
    long user_id;

    @Element(required = false)
    long votes;



    @Element(required = false)
    long weight;

    @Element(name = "work-id", required = false)
    long workId;

    @Element(required = false)
    long work_id;

    @Element(required = false)
    UserBook book;

    @Element(required = false)
    long book_id;

    @Element(required = false)
    boolean spoiler_flag;

    @Element(required = false)
    String spoilers_state;

    @ElementList(required = false)
    List<Shelves> shelves = new ArrayList<>();

    @Element(required = false)
    String recommended_for;

    @Element(required = false)
    String recommended_by;

    @Element(required = false)
    String started_at;

    @Element(required = false)
    String read_at;

    @Element(required = false)
    String date_added;

    @Element(required = false)
    String date_updated;

    @Element(required = false)
    long read_count;

    @Element(required = false)
    String body;

    @Element(required = false)
    long comments_count;

    @Element(required = false)
    String url;

    @Element(required = false)
    String link;

    @Override
    public String toString() {
        return "Review{" +
                "bookId=" + bookId +
                ", commentsCount=" + commentsCount +
                ", createdAt='" + createdAt + '\'' +
                ", hiddenFlag=" + hiddenFlag +
                ", id=" + id +
                ", languageCode=" + languageCode +
                ", lastCommentAt='" + lastCommentAt + '\'' +
                ", lastRevisionAt='" + lastRevisionAt + '\'' +
                ", nonFriendsRatingCount=" + nonFriendsRatingCount +
                ", notes='" + notes + '\'' +
                ", rating=" + rating +
                ", ratingsCount=" + ratingsCount +
                ", ratingsSum=" + ratingsSum +
                ", readAt='" + readAt + '\'' +
                ", readCount='" + readCount + '\'' +
                ", readStatus='" + readStatus + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", recommenderUserId=" + recommenderUserId +
                ", recommenderUserName='" + recommenderUserName + '\'' +
                ", review='" + review + '\'' +
                ", sellFlag=" + sellFlag +
                ", spoilerFlag=" + spoilerFlag +
                ", startedAt='" + startedAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", userId=" + userId +
                ", weight=" + weight +
                ", workId=" + workId +
                ", book=" + book +
                '}';
    }

    public UserBook getBook() {
        return book;
    }
    public void setBook(UserBook value) {this.book = value;}



    public String getRecommendedFor() {return this.recommended_for;}
    public void setRecommendedFor(String value) {this.recommended_for = value;}

    public String getDateUpdated() {return this.date_updated;}
    public void setDateUpdated(String value) {this.date_updated = value;}



    public void setRating(long value) {this.rating = value;}

    public String getLink() {return this.link;}
    public void setLink(String value) {this.link = value;}

    public String getBody() {return this.body;}
    public void setBody(String value) {this.body = value;}

    public String getUrl() {return this.url;}
    public void setUrl(String value) {this.url = value;}

    public String getDateAdded() {return this.date_added;}
    public void setDateAdded(String value) {this.date_added = value;}

    public long getCommentsCount() {return this.commentsCount;}
    public void setCommentsCount(long value) {this.commentsCount = value;}

    public long getComments_Count() {return this.comments_count;}
    public void setComments_Count(long value) {this.comments_count = value;}

    public String getStartedAt() {return this.startedAt;}
    public void setStartedAt(String value) {this.startedAt = value;}

    public String getReadAt() {return this.readAt;}
    public void setReadAt(String value) {this.readAt = value;}

    public long getVotes() {return this.votes;}
    public void setVotes(long value) {this.votes = value;}

    public String getRecommendedBy() {return this.recommended_by;}
    public void setRecommendedBy(String value) {this.recommended_by = value;}

    public long getId() {return this.id;}
    public void setId(long value) {this.id = value;}

    public String getSpoilersState() {return this.spoilers_state;}
    public void setSpoilersState(String value) {this.spoilers_state = value;}

    public String getReadCount() {return this.readCount;}
    public void setReadCount(String value) {this.readCount = value;}

    public long getRating() {
        return rating;
    }*/
}
