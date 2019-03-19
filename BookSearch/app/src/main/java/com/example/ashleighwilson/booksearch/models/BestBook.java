package com.example.ashleighwilson.booksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "best_book", strict = false)
public class BestBook implements Parcelable {

    public BestBook(@Element(name="author", required=false)
                            Author author,

                            @Element(name="image_url", required=false)
                                    String imageUrl,

                            @Element(name="id", required=false)
                                    String id,

                            @Element(name="title", required=false)
                                    String title) {

        this.author = author;
        this.imageUrl = imageUrl;
        this.id = id;
        this.title = title;
    }
    @Element(name="author", required=false)
    Author author;

    @Element(name="image_url", required=false)
    String imageUrl;

    @Element(name="id", required=false)
    String id;

    @Element(name="title", required=false)
    String title;

    protected BestBook(Parcel in) {
        author = in.readParcelable(Author.class.getClassLoader());
        imageUrl = in.readString();
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<BestBook> CREATOR = new Creator<BestBook>() {
        @Override
        public BestBook createFromParcel(Parcel in) {
            return new BestBook(in);
        }

        @Override
        public BestBook[] newArray(int size) {
            return new BestBook[size];
        }
    };

    public Author getAuthor() {return this.author;}
    public void setAuthor(Author value) {this.author = value;}

    public String getImageUrl() {return this.imageUrl;}
    public void setImageUrl(String value) {this.imageUrl = value;}

    public String getId() {return this.id;}
    public void setId(String value) {this.id = value;}

    public String getTitle() {return this.title;}
    public void setTitle(String value) {this.title = value;}

    public String getAltBookCover(String identifier) {
        return "https://images.randomhouse.com/cover/" + identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(author, flags);
        dest.writeString(imageUrl);
        dest.writeString(id);
        dest.writeString(title);
    }
}
