package main;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.List;

@Root(name="shelf")
public class Shelf {

    @Element(name="review-id", required=false)
    ReviewId reviewId;

    @Element(name="updated-at", required=false)
    UpdatedAt updatedAt;

    @Element(name="name", required=false)
    String name;

    @Element(name="exclusive", required=false)
    Exclusive exclusive;

    @Element(name="created-at", required=false)
    CreatedAt createdAt;

    @Element(name="id", required=false)
    Id id;

    @Element(name="position", required=false)
    Position position;

    @Element(name="user-shelf-id", required=false)
    UserShelfId userShelfId;

    public ReviewId getReviewId() {return this.reviewId;}
    public void setReviewId(ReviewId value) {this.reviewId = value;}

    public UpdatedAt getUpdatedAt() {return this.updatedAt;}
    public void setUpdatedAt(UpdatedAt value) {this.updatedAt = value;}

    public String getName() {return this.name;}
    public void setName(String value) {this.name = value;}

    public Exclusive getExclusive() {return this.exclusive;}
    public void setExclusive(Exclusive value) {this.exclusive = value;}

    public CreatedAt getCreatedAt() {return this.createdAt;}
    public void setCreatedAt(CreatedAt value) {this.createdAt = value;}

    public Id getId() {return this.id;}
    public void setId(Id value) {this.id = value;}

    public Position getPosition() {return this.position;}
    public void setPosition(Position value) {this.position = value;}

    public UserShelfId getUserShelfId() {return this.userShelfId;}
    public void setUserShelfId(UserShelfId value) {this.userShelfId = value;}

    public static class ReviewId {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class UpdatedAt {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Exclusive {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class CreatedAt {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Id {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Position {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class UserShelfId {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

}