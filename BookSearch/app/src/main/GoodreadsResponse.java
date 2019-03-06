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

    @Element(name="reviews", required=false)
    Reviews reviews;

    @Element(name="Request", required=false)
    Request request;

    public Reviews getReviews() {return this.reviews;}
    public void setReviews(Reviews value) {this.reviews = value;}

    public Request getRequest() {return this.request;}
    public void setRequest(Request value) {this.request = value;}

    public static class Book {

        @Element(name="image_url", required=false)
        String imageUrl;

        @Element(name="work", required=false)
        Work work;

        @Element(name="isbn", required=false)
        Isbn isbn;

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

        @Element(name="publication_month", required=false)
        String publicationMonth;

        @Element(name="large_image_url", required=false)
        String largeImageUrl;

        @Element(name="publication_year", required=false)
        String publicationYear;

        @Element(name="small_image_url", required=false)
        String smallImageUrl;

        @Element(name="text_reviews_count", required=false)
        TextReviewsCount textReviewsCount;

        @Element(name="isbn13", required=false)
        Isbn13 isbn13;

        @Element(name="edition_information", required=false)
        String editionInformation;

        @Element(name="publisher", required=false)
        String publisher;

        @Element(name="publication_day", required=false)
        String publicationDay;

        @Element(name="id", required=false)
        Id id;

        @Element(name="ratings_count", required=false)
        String ratingsCount;

        @Element(name="title_without_series", required=false)
        String titleWithoutSeries;

        @Element(name="authors", required=false)
        Authors authors;

        public String getImageUrl() {return this.imageUrl;}
        public void setImageUrl(String value) {this.imageUrl = value;}

        public Work getWork() {return this.work;}
        public void setWork(Work value) {this.work = value;}

        public Isbn getIsbn() {return this.isbn;}
        public void setIsbn(Isbn value) {this.isbn = value;}

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

        public TextReviewsCount getTextReviewsCount() {return this.textReviewsCount;}
        public void setTextReviewsCount(TextReviewsCount value) {this.textReviewsCount = value;}

        public Isbn13 getIsbn13() {return this.isbn13;}
        public void setIsbn13(Isbn13 value) {this.isbn13 = value;}

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

        public Authors getAuthors() {return this.authors;}
        public void setAuthors(Authors value) {this.authors = value;}

    }

    public static class Isbn {

        @Attribute(name="nil", required=false)
        Boolean nil;

        public Boolean getNil() {return this.nil;}
        public void setNil(Boolean value) {this.nil = value;}

    }

    public static class Link {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Body {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Shelves {

        @Element(name="shelf", required=false)
        Shelf shelf;

        public Shelf getShelf() {return this.shelf;}
        public void setShelf(Shelf value) {this.shelf = value;}

    }

    public static class SmallImageUrl {

        @Attribute(name="nophoto", required=false)
        Boolean nophoto;

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public Boolean getNophoto() {return this.nophoto;}
        public void setNophoto(Boolean value) {this.nophoto = value;}

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Reviews {

        @Attribute(name="total", required=false)
        Integer total;

        @ElementList(name="review", required=false, entry="review", inline=true)
        List<Review> review;

        @Attribute(name="start", required=false)
        Integer start;

        @Attribute(name="end", required=false)
        Integer end;

        public Integer getTotal() {return this.total;}
        public void setTotal(Integer value) {this.total = value;}

        public List<Review> getReview() {return this.review;}
        public void setReview(List<Review> value) {this.review = value;}

        public Integer getStart() {return this.start;}
        public void setStart(Integer value) {this.start = value;}

        public Integer getEnd() {return this.end;}
        public void setEnd(Integer value) {this.end = value;}

    }

    public static class TextReviewsCount {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Review {

        @Element(name="recommended_for", required=false)
        String recommendedFor;

        @Element(name="date_updated", required=false)
        String dateUpdated;

        @Element(name="spoiler_flag", required=false)
        String spoilerFlag;

        @Element(name="book", required=false)
        Book book;

        @Element(name="rating", required=false)
        String rating;

        @Element(name="link", required=false)
        Link link;

        @Element(name="body", required=false)
        String body;

        @Element(name="shelves", required=false)
        Shelves shelves;

        @Element(name="url", required=false)
        Url url;

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

        public Book getBook() {return this.book;}
        public void setBook(Book value) {this.book = value;}

        public String getRating() {return this.rating;}
        public void setRating(String value) {this.rating = value;}

        public Link getLink() {return this.link;}
        public void setLink(Link value) {this.link = value;}

        public String getBody() {return this.body;}
        public void setBody(String value) {this.body = value;}

        public Shelves getShelves() {return this.shelves;}
        public void setShelves(Shelves value) {this.shelves = value;}

        public Url getUrl() {return this.url;}
        public void setUrl(Url value) {this.url = value;}

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

    public static class Key {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Method {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Author {

        @Element(name="role", required=false)
        String role;

        @Element(name="small_image_url", required=false)
        SmallImageUrl smallImageUrl;

        @Element(name="text_reviews_count", required=false)
        String textReviewsCount;

        @Element(name="image_url", required=false)
        ImageUrl imageUrl;

        @Element(name="name", required=false)
        String name;

        @Element(name="link", required=false)
        Link link;

        @Element(name="average_rating", required=false)
        String averageRating;

        @Element(name="id", required=false)
        String id;

        @Element(name="ratings_count", required=false)
        String ratingsCount;

        public String getRole() {return this.role;}
        public void setRole(String value) {this.role = value;}

        public SmallImageUrl getSmallImageUrl() {return this.smallImageUrl;}
        public void setSmallImageUrl(SmallImageUrl value) {this.smallImageUrl = value;}

        public String getTextReviewsCount() {return this.textReviewsCount;}
        public void setTextReviewsCount(String value) {this.textReviewsCount = value;}

        public ImageUrl getImageUrl() {return this.imageUrl;}
        public void setImageUrl(ImageUrl value) {this.imageUrl = value;}

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

        public Link getLink() {return this.link;}
        public void setLink(Link value) {this.link = value;}

        public String getAverageRating() {return this.averageRating;}
        public void setAverageRating(String value) {this.averageRating = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public String getRatingsCount() {return this.ratingsCount;}
        public void setRatingsCount(String value) {this.ratingsCount = value;}

    }

    public static class ImageUrl {

        @Attribute(name="nophoto", required=false)
        Boolean nophoto;

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public Boolean getNophoto() {return this.nophoto;}
        public void setNophoto(Boolean value) {this.nophoto = value;}

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Work {

        @Element(name="id", required=false)
        String id;

        @Element(name="uri", required=false)
        String uri;

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public String getUri() {return this.uri;}
        public void setUri(String value) {this.uri = value;}

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

    public static class Url {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Shelf {

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

    public static class Isbn13 {

        @Attribute(name="nil", required=false)
        Boolean nil;

        public Boolean getNil() {return this.nil;}
        public void setNil(Boolean value) {this.nil = value;}

    }

    public static class Authors {

        @Element(name="author", required=false)
        Author author;

        public Author getAuthor() {return this.author;}
        public void setAuthor(Author value) {this.author = value;}

    }

}