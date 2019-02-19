package com.example.ashleighwilson.booksearch.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Reader implements Parcelable {

    private Bitmap mCoverImage;
    private String mTitle;
    private String mAuthor;
    private String pathLocation;
    private int mId;

    public Reader(){}

    public Reader(Bitmap coverImage) {
        this.mCoverImage = coverImage;
    }

    protected Reader(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mAuthor = in.readString();
        mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
        pathLocation = in.readString();
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public Bitmap getmCoverImage() {
        return mCoverImage;
    }

    public void setmCoverImage(Bitmap mCoverImage) {
        this.mCoverImage = mCoverImage;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPathLocation() {
        return pathLocation;
    }

    public void setPathLocation(String pathLocation) {
        this.pathLocation = pathLocation;
    }

    public static final Creator<Reader> CREATOR = new Creator<Reader>() {
        @Override
        public Reader createFromParcel(Parcel in) {
            return new Reader(in);
        }

        @Override
        public Reader[] newArray(int size) {
            return new Reader[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeParcelable(mCoverImage, flags);
        dest.writeString(pathLocation);
    }
}
