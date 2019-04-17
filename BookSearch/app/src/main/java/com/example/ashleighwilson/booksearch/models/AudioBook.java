package com.example.ashleighwilson.booksearch.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class AudioBook implements Parcelable {

    private String mName;
    private String mAuthor;
    private String mImage;
    private String mPublished;
    private String mFilePath;
    private int mId;
    private long mLength;
    private long mTime;
    private Uri mUri;
    private String mMime_type;
    private long mSize;
    private int mCurrentPosition;

    public AudioBook(String name, String author, String image, String published, String path) {
        this.mName = name;
        this.mAuthor = author;
        this.mImage = image;
        this.mPublished = published;
        this.mFilePath = path;
    }

    protected AudioBook(Parcel in) {
        mName = in.readString();
        mAuthor = in.readString();
        mImage = in.readString();
        mPublished = in.readString();
        mFilePath = in.readString();
        mId = in.readInt();
        mLength = in.readLong();
        mTime = in.readLong();
        mUri = in.readParcelable(Uri.class.getClassLoader());
        mMime_type = in.readString();
        mSize = in.readLong();
        mCurrentPosition = in.readInt();
    }

    public static final Creator<AudioBook> CREATOR = new Creator<AudioBook>() {
        @Override
        public AudioBook createFromParcel(Parcel in) {
            return new AudioBook(in);
        }

        @Override
        public AudioBook[] newArray(int size) {
            return new AudioBook[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmPublished() {
        return mPublished;
    }

    public void setmPublished(String mPublished) {
        this.mPublished = mPublished;
    }

    public String getmFilePath() {
        return mFilePath;
    }

    public void setmFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public long getmLength() {
        return mLength;
    }

    public void setmLength(long mLength) {
        this.mLength = mLength;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public Uri getmUri() {
        return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    public String getmMime_type() {
        return mMime_type;
    }

    public void setmMime_type(String mMime_type) {
        this.mMime_type = mMime_type;
    }

    public long getmSize() {
        return mSize;
    }

    public void setmSize(long mSize) {
        this.mSize = mSize;
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mAuthor);
        dest.writeString(mImage);
        dest.writeString(mPublished);
        dest.writeString(mFilePath);
        dest.writeInt(mId);
        dest.writeLong(mLength);
        dest.writeLong(mTime);
        dest.writeParcelable(mUri, flags);
        dest.writeString(mMime_type);
        dest.writeLong(mSize);
        dest.writeInt(mCurrentPosition);
    }
}
