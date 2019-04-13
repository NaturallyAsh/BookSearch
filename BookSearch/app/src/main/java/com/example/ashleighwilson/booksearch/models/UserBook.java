package com.example.ashleighwilson.booksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

public class UserBook implements Parcelable{

    @Root(name = "book", strict = false)
    public UserBook(@Element(name="image_url", required=false)
                            String imageUrl,

                            @Element(name="num_pages", required=false)
                                    String numPages,

                            @Element(name="link", required=false)
                                    String link,

                            @Element(name="format", required=false)
                                    String format,

                            @Element(name="description", required=false)
                                    String description,

                            @Element(name="average_rating", required=false)
                                    String averageRating,

                            @Element(name="published", required=false)
                                    String published,

                            @Element(name="title", required=false)
                                    String title,

                            @Element(name="uri", required=false)
                                    String uri,

                            @Element(name="id", required=false)
                                    Id id,

                            @Element(name="publication_month", required=false)
                                    String publicationMonth,

                            @Element(name="large_image_url", required=false)
                                    String largeImageUrl,

                            @Element(name="publication_year", required=false)
                                    String publicationYear,

                            @Element(name="small_image_url", required=false)
                                    String smallImageUrl,

                            @Element(name="edition_information", required=false)
                                    String editionInformation,

                            @Element(name="publisher", required=false)
                                    String publisher,

                            @Element(name="publication_day", required=false)
                                    String publicationDay,

                            @Element(name="ratings_count", required=false)
                                    String ratingsCount,

                            @Element(name="title_without_series", required=false)
                                    String titleWithoutSeries,

                            @Element(name="authors", required=false)
                                    Authors authors,

                            @Element(name="text_reviews_count", required=false)
                                    TextReviewsCount textReviewsCount,

                            @Element(name="isbn", required=false)
                                    String isbn,

                            @Element(name = "work", required = false)
                                    Work work,

                            @Element(name="asin", required=false)
                                    String asin,

                            @Element(name="reviews_widget", required=false)
                                    String reviewsWidget,

                            //to avoid duplicate error, made series works an elementlist
                            //remember to send straight to series work (not series works)
                            @ElementList(name = "series_works", required = false)
                            List<SeriesWork> seriesWorks,

                            @ElementList(name="similar_books", required=false)
                                    List<UserBook> similarBooks,
                            @Element(name="my_review", required=false)
                                MyReview myReview) {

        this.imageUrl = imageUrl;
        this.numPages = numPages;
        this.link = link;
        this.format = format;
        this.description = description;
        this.averageRating = averageRating;
        this.published = published;
        this.title = title;
        this.uri = uri;
        this.id = id;
        this.publicationMonth = publicationMonth;
        this.largeImageUrl = largeImageUrl;
        this.publicationYear = publicationYear;
        this.smallImageUrl = smallImageUrl;
        this.editionInformation = editionInformation;
        this.publisher = publisher;
        this.publicationDay = publicationDay;
        this.ratingsCount = ratingsCount;
        this.titleWithoutSeries = titleWithoutSeries;
        this.authors = authors;
        this.textReviewsCount = textReviewsCount;
        this.isbn = isbn;
        this.work = work;
        this.asin = asin;
        this.reviewsWidget = reviewsWidget;
        this.seriesWorks = seriesWorks;
        //this.seriesWorksList = seriesWorksList;
        this.similarBooks = similarBooks;
    }


    @Element(name="image_url", required=false)
    String imageUrl;

    @Element(name="num_pages", required=false)
    String numPages;

    @Element(name="link", required=false)
    String link;

    @Element(name="format", required=false)
    String format;

    @Element(name="description", required=false)
    String description;

    @Element(name="average_rating", required=false)
    String averageRating;

    @Element(name="published", required=false)
    String published;

    @Element(name="title", required=false)
    String title;

    @Element(name="uri", required=false)
    String uri;

    @Element(name="id", required=false)
    Id id;

    @Element(name="publication_month", required=false)
    String publicationMonth;

    @Element(name="large_image_url", required=false)
    String largeImageUrl;

    @Element(name="publication_year", required=false)
    String publicationYear;

    @Element(name="small_image_url", required=false)
    String smallImageUrl;

    @Element(name="edition_information", required=false)
    String editionInformation;

    @Element(name="publisher", required=false)
    String publisher;

    @Element(name="publication_day", required=false)
    String publicationDay;

    @Element(name="ratings_count", required=false)
    String ratingsCount;

    @Element(name="title_without_series", required=false)
    String titleWithoutSeries;

    @Element(name="authors", required=false)
    Authors authors;

    @Element(name="text_reviews_count", required=false)
    TextReviewsCount textReviewsCount;

    @Element(name="isbn", required=false)
    String isbn;

    @Element(name = "work", required = false)
    Work work;

    @Element(name="asin", required=false)
    String asin;

    @Element(name="reviews_widget", required=false)
    String reviewsWidget;

    //to avoid duplicate error, made series works an elementlist
    //remember to send straight to series work (not series works)
    @ElementList(name = "series_works", required = false)
    List<SeriesWork> seriesWorks;

    @ElementList(name="similar_books", required=false)
    List<UserBook> similarBooks;

    @Element(name="my_review", required=false)
    MyReview myReview;

    protected UserBook(Parcel in) {
        imageUrl = in.readString();
        numPages = in.readString();
        link = in.readString();
        format = in.readString();
        description = in.readString();
        averageRating = in.readString();
        published = in.readString();
        title = in.readString();
        uri = in.readString();
        publicationMonth = in.readString();
        largeImageUrl = in.readString();
        publicationYear = in.readString();
        smallImageUrl = in.readString();
        editionInformation = in.readString();
        publisher = in.readString();
        publicationDay = in.readString();
        ratingsCount = in.readString();
        titleWithoutSeries = in.readString();
        isbn = in.readString();
        asin = in.readString();
        reviewsWidget = in.readString();
        similarBooks = in.createTypedArrayList(UserBook.CREATOR);
    }

    public static final Creator<UserBook> CREATOR = new Creator<UserBook>() {
        @Override
        public UserBook createFromParcel(Parcel in) {
            return new UserBook(in);
        }

        @Override
        public UserBook[] newArray(int size) {
            return new UserBook[size];
        }
    };

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static UserBook fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserBook.class);
    }

    public MyReview getMyReview() {return this.myReview;}
    public void setMyReview(MyReview value) {this.myReview = value;}

    public List<UserBook> getSimilarBooks() {return this.similarBooks;}
    public void setSimilarBooks(List<UserBook> value) {this.similarBooks = value;}

    //public SeriesWorks getSeriesWorks() {return this.seriesWorks;}
    //public void setSeriesWorks(SeriesWorks value) {this.seriesWorks = value;}

    public List<SeriesWork> getSeriesWorks() {
        return seriesWorks;
    }

    public String getReviewsWidget() {return this.reviewsWidget;}
    public void setReviewsWidget(String value) {this.reviewsWidget = value;}

    public String getAltBookCover(String identifier) {
        return "https://images.randomhouse.com/cover/" + identifier;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getAsin() {return this.asin;}
    public void setAsin(String value) {this.asin = value;}

    public String getImageUrl() {return this.imageUrl;}
    public void setImageUrl(String value) {this.imageUrl = value;}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumPages() {return this.numPages;}
    public void setNumPages(String value) {this.numPages = value;}

    public String getLink() {return this.link;}
    public void setLink(String value) {this.link = value;}

    public String getFormat() {return this.format;}
    public void setFormat(String value) {this.format = value;}

    public String getDescription() {return this.description;}
    public void setDescription(String value) {this.description = value;}

    public String getAverageRating() {return this.averageRating;}
    public void setAverageRating(String value) {this.averageRating = value;}

    public String getPublished() {return this.published;}
    public void setPublished(String value) {this.published = value;}

    public String getTitle() {return this.title;}
    public void setTitle(String value) {this.title = value;}

    public String getUri() {return this.uri;}
    public void setUri(String value) {this.uri = value;}

    public String getPublicationMonth() {return this.publicationMonth;}
    public void setPublicationMonth(String value) {this.publicationMonth = value;}

    public String getLargeImageUrl() {return this.largeImageUrl;}
    public void setLargeImageUrl(String value) {this.largeImageUrl = value;}

    public String getPublicationYear() {return this.publicationYear;}
    public void setPublicationYear(String value) {this.publicationYear = value;}

    public String getSmallImageUrl() {return this.smallImageUrl;}
    public void setSmallImageUrl(String value) {this.smallImageUrl = value;}

    public String getEditionInformation() {return this.editionInformation;}
    public void setEditionInformation(String value) {this.editionInformation = value;}

    public String getPublisher() {return this.publisher;}
    public void setPublisher(String value) {this.publisher = value;}

    public String getPublicationDay() {return this.publicationDay;}
    public void setPublicationDay(String value) {this.publicationDay = value;}

    public Id getId() {return this.id;}
    public void setId(Id value) {this.id = value;}

    public String getRatingsCount() {return this.ratingsCount;}
    public void setRatingsCount(String value) {this.ratingsCount = value;}

    public String getTitleWithoutSeries() {return this.titleWithoutSeries;}
    public void setTitleWithoutSeries(String value) {this.titleWithoutSeries = value;}

    public Authors getAuthor() {return this.authors;}
    public void setAuthor(Authors value) {this.authors = value;}

    public TextReviewsCount getTextReviewsCount() {return this.textReviewsCount;}
    public void setTextReviewsCount(TextReviewsCount value) {this.textReviewsCount = value;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(numPages);
        dest.writeString(link);
        dest.writeString(format);
        dest.writeString(description);
        dest.writeString(averageRating);
        dest.writeString(published);
        dest.writeString(title);
        dest.writeString(uri);
        dest.writeString(publicationMonth);
        dest.writeString(largeImageUrl);
        dest.writeString(publicationYear);
        dest.writeString(smallImageUrl);
        dest.writeString(editionInformation);
        dest.writeString(publisher);
        dest.writeString(publicationDay);
        dest.writeString(ratingsCount);
        dest.writeString(titleWithoutSeries);
        dest.writeString(isbn);
        dest.writeString(asin);
        dest.writeString(reviewsWidget);
        dest.writeTypedList(similarBooks);
    }
}
