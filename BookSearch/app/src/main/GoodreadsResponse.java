package main;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.List;

@Root(name="GoodreadsResponse")
public class GoodreadsResponse {

    @Element(name="series", required=false)
    Series series;

    @Element(name="Request", required=false)
    Request request;

    public Series getSeries() {return this.series;}
    public void setSeries(Series value) {this.series = value;}

    public Request getRequest() {return this.request;}
    public void setRequest(Request value) {this.request = value;}

    public static class Method {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Work {

        @Element(name="books_count", required=false)
        String booksCount;

        @Element(name="original_publication_month", required=false)
        String originalPublicationMonth;

        @Element(name="original_title", required=false)
        String originalTitle;

        @Element(name="average_rating", required=false)
        String averageRating;

        @Element(name="original_publication_year", required=false)
        String originalPublicationYear;

        @Element(name="uri", required=false)
        String uri;

        @Element(name="best_book", required=false)
        BestBook bestBook;

        @Element(name="text_reviews_count", required=false)
        String textReviewsCount;

        @Element(name="original_publication_day", required=false)
        String originalPublicationDay;

        @Element(name="ratings_sum", required=false)
        String ratingsSum;

        @Element(name="id", required=false)
        String id;

        @Element(name="ratings_count", required=false)
        String ratingsCount;

        @Element(name="reviews_count", required=false)
        String reviewsCount;

        public String getBooksCount() {return this.booksCount;}
        public void setBooksCount(String value) {this.booksCount = value;}

        public String getOriginalPublicationMonth() {return this.originalPublicationMonth;}
        public void setOriginalPublicationMonth(String value) {this.originalPublicationMonth = value;}

        public String getOriginalTitle() {return this.originalTitle;}
        public void setOriginalTitle(String value) {this.originalTitle = value;}

        public String getAverageRating() {return this.averageRating;}
        public void setAverageRating(String value) {this.averageRating = value;}

        public String getOriginalPublicationYear() {return this.originalPublicationYear;}
        public void setOriginalPublicationYear(String value) {this.originalPublicationYear = value;}

        public String getUri() {return this.uri;}
        public void setUri(String value) {this.uri = value;}

        public BestBook getBestBook() {return this.bestBook;}
        public void setBestBook(BestBook value) {this.bestBook = value;}

        public String getTextReviewsCount() {return this.textReviewsCount;}
        public void setTextReviewsCount(String value) {this.textReviewsCount = value;}

        public String getOriginalPublicationDay() {return this.originalPublicationDay;}
        public void setOriginalPublicationDay(String value) {this.originalPublicationDay = value;}

        public String getRatingsSum() {return this.ratingsSum;}
        public void setRatingsSum(String value) {this.ratingsSum = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public String getRatingsCount() {return this.ratingsCount;}
        public void setRatingsCount(String value) {this.ratingsCount = value;}

        public String getReviewsCount() {return this.reviewsCount;}
        public void setReviewsCount(String value) {this.reviewsCount = value;}

    }

    public static class Author {

        @Element(name="name", required=false)
        String name;

        @Element(name="id", required=false)
        String id;

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

    }

    public static class ImageUrl {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Request {

        @Element(name="method", required=false)
        Method method;

        @Element(name="key", required=false)
        Key key;

        @Element(name="authentication", required=false)
        String authentication;

        public Method getMethod() {return this.method;}
        public void setMethod(Method value) {this.method = value;}

        public Key getKey() {return this.key;}
        public void setKey(Key value) {this.key = value;}

        public String getAuthentication() {return this.authentication;}
        public void setAuthentication(String value) {this.authentication = value;}

    }

    public static class Title {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class BestBook {

        @Element(name="author", required=false)
        Author author;

        @Element(name="image_url", required=false)
        ImageUrl imageUrl;

        @Element(name="id", required=false)
        String id;

        @Element(name="title", required=false)
        String title;

        public Author getAuthor() {return this.author;}
        public void setAuthor(Author value) {this.author = value;}

        public ImageUrl getImageUrl() {return this.imageUrl;}
        public void setImageUrl(ImageUrl value) {this.imageUrl = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public String getTitle() {return this.title;}
        public void setTitle(String value) {this.title = value;}

    }

    public static class Series {

        @Element(name="note", required=false)
        String note;

        @Element(name="series_works_count", required=false)
        String seriesWorksCount;

        @ElementList(name="series_works", required=false)
        List<SeriesWork> seriesWorks;

        @Element(name="numbered", required=false)
        String numbered;

        @Element(name="description", required=false)
        String description;

        @Element(name="id", required=false)
        String id;

        @Element(name="title", required=false)
        Title title;

        @Element(name="primary_work_count", required=false)
        String primaryWorkCount;

        public String getNote() {return this.note;}
        public void setNote(String value) {this.note = value;}

        public String getSeriesWorksCount() {return this.seriesWorksCount;}
        public void setSeriesWorksCount(String value) {this.seriesWorksCount = value;}

        public List<SeriesWork> getSeriesWorks() {return this.seriesWorks;}
        public void setSeriesWorks(List<SeriesWork> value) {this.seriesWorks = value;}

        public String getNumbered() {return this.numbered;}
        public void setNumbered(String value) {this.numbered = value;}

        public String getDescription() {return this.description;}
        public void setDescription(String value) {this.description = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public Title getTitle() {return this.title;}
        public void setTitle(Title value) {this.title = value;}

        public String getPrimaryWorkCount() {return this.primaryWorkCount;}
        public void setPrimaryWorkCount(String value) {this.primaryWorkCount = value;}

    }

    public static class SeriesWork {

        @Element(name="work", required=false)
        Work work;

        @Element(name="user_position", required=false)
        String userPosition;

        @Element(name="id", required=false)
        String id;

        public Work getWork() {return this.work;}
        public void setWork(Work value) {this.work = value;}

        public String getUserPosition() {return this.userPosition;}
        public void setUserPosition(String value) {this.userPosition = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

    }

    public static class Key {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

}