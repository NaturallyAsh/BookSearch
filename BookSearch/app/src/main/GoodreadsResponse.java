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

    @Element(name="book", required=false)
    Book book;

    @Element(name="Request", required=false)
    Request request;

    public Book getBook() {return this.book;}
    public void setBook(Book value) {this.book = value;}

    public Request getRequest() {return this.request;}
    public void setRequest(Request value) {this.request = value;}

    public static class MarketplaceId {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class BookLinks {

        @Text(required=false)
        String textValue;

        @Element(name="book_link", required=false)
        BookLink bookLink;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public BookLink getBookLink() {return this.bookLink;}
        public void setBookLink(BookLink value) {this.bookLink = value;}

    }

    public static class NumPages {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class OriginalLanguageId {

        @Attribute(name="nil", required=false)
        Boolean nil;

        @Attribute(name="type", required=false)
        String type;

        public Boolean getNil() {return this.nil;}
        public void setNil(Boolean value) {this.nil = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class OriginalPublicationDay {

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

    public static class MyReview {

        @Element(name="recommended_for", required=false)
        String recommendedFor;

        @Element(name="date_updated", required=false)
        String dateUpdated;

        @Element(name="spoiler_flag", required=false)
        String spoilerFlag;

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

    public static class RatingsCount {

        @Text(required=false)
        String textValue;

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Method {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class OriginalPublicationMonth {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Work {

        @Element(name="default_description_language_code", required=false)
        DefaultDescriptionLanguageCode defaultDescriptionLanguageCode;

        @Element(name="books_count", required=false)
        BooksCount booksCount;

        @Element(name="original_publication_month", required=false)
        OriginalPublicationMonth originalPublicationMonth;

        @Element(name="original_title", required=false)
        String originalTitle;

        @Element(name="original_publication_year", required=false)
        OriginalPublicationYear originalPublicationYear;

        @Element(name="rating_dist", required=false)
        String ratingDist;

        @Element(name="original_language_id", required=false)
        OriginalLanguageId originalLanguageId;

        @Element(name="best_book_id", required=false)
        BestBookId bestBookId;

        @Element(name="text_reviews_count", required=false)
        TextReviewsCount textReviewsCount;

        @Element(name="media_type", required=false)
        String mediaType;

        @Element(name="original_publication_day", required=false)
        OriginalPublicationDay originalPublicationDay;

        @Element(name="default_chaptering_book_id", required=false)
        DefaultChapteringBookId defaultChapteringBookId;

        @Element(name="ratings_sum", required=false)
        RatingsSum ratingsSum;

        @Element(name="id", required=false)
        String id;

        @Element(name="ratings_count", required=false)
        RatingsCount ratingsCount;

        @Element(name="reviews_count", required=false)
        ReviewsCount reviewsCount;

        @Element(name="desc_user_id", required=false)
        DescUserId descUserId;

        public DefaultDescriptionLanguageCode getDefaultDescriptionLanguageCode() {return this.defaultDescriptionLanguageCode;}
        public void setDefaultDescriptionLanguageCode(DefaultDescriptionLanguageCode value) {this.defaultDescriptionLanguageCode = value;}

        public BooksCount getBooksCount() {return this.booksCount;}
        public void setBooksCount(BooksCount value) {this.booksCount = value;}

        public OriginalPublicationMonth getOriginalPublicationMonth() {return this.originalPublicationMonth;}
        public void setOriginalPublicationMonth(OriginalPublicationMonth value) {this.originalPublicationMonth = value;}

        public String getOriginalTitle() {return this.originalTitle;}
        public void setOriginalTitle(String value) {this.originalTitle = value;}

        public OriginalPublicationYear getOriginalPublicationYear() {return this.originalPublicationYear;}
        public void setOriginalPublicationYear(OriginalPublicationYear value) {this.originalPublicationYear = value;}

        public String getRatingDist() {return this.ratingDist;}
        public void setRatingDist(String value) {this.ratingDist = value;}

        public OriginalLanguageId getOriginalLanguageId() {return this.originalLanguageId;}
        public void setOriginalLanguageId(OriginalLanguageId value) {this.originalLanguageId = value;}

        public BestBookId getBestBookId() {return this.bestBookId;}
        public void setBestBookId(BestBookId value) {this.bestBookId = value;}

        public TextReviewsCount getTextReviewsCount() {return this.textReviewsCount;}
        public void setTextReviewsCount(TextReviewsCount value) {this.textReviewsCount = value;}

        public String getMediaType() {return this.mediaType;}
        public void setMediaType(String value) {this.mediaType = value;}

        public OriginalPublicationDay getOriginalPublicationDay() {return this.originalPublicationDay;}
        public void setOriginalPublicationDay(OriginalPublicationDay value) {this.originalPublicationDay = value;}

        public DefaultChapteringBookId getDefaultChapteringBookId() {return this.defaultChapteringBookId;}
        public void setDefaultChapteringBookId(DefaultChapteringBookId value) {this.defaultChapteringBookId = value;}

        public RatingsSum getRatingsSum() {return this.ratingsSum;}
        public void setRatingsSum(RatingsSum value) {this.ratingsSum = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public RatingsCount getRatingsCount() {return this.ratingsCount;}
        public void setRatingsCount(RatingsCount value) {this.ratingsCount = value;}

        public ReviewsCount getReviewsCount() {return this.reviewsCount;}
        public void setReviewsCount(ReviewsCount value) {this.reviewsCount = value;}

        public DescUserId getDescUserId() {return this.descUserId;}
        public void setDescUserId(DescUserId value) {this.descUserId = value;}

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

    public static class Format {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Shelf {

        @Attribute(name="review_shelf_id", required=false)
        Long reviewShelfId;

        @Attribute(name="name", required=false)
        String name;

        @Attribute(name="count", required=false)
        Integer count;

        @Attribute(name="exclusive", required=false)
        Boolean exclusive;

        @Attribute(name="id", required=false)
        Integer id;

        @Attribute(name="sortable", required=false)
        Boolean sortable;

        public Long getReviewShelfId() {return this.reviewShelfId;}
        public void setReviewShelfId(Long value) {this.reviewShelfId = value;}

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

        public Integer getCount() {return this.count;}
        public void setCount(Integer value) {this.count = value;}

        public Boolean getExclusive() {return this.exclusive;}
        public void setExclusive(Boolean value) {this.exclusive = value;}

        public Integer getId() {return this.id;}
        public void setId(Integer value) {this.id = value;}

        public Boolean getSortable() {return this.sortable;}
        public void setSortable(Boolean value) {this.sortable = value;}

    }

    public static class CountryCode {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Isbn13 {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class RatingsSum {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class BookLink {

        @Element(name="name", required=false)
        String name;

        @Element(name="link", required=false)
        String link;

        @Element(name="id", required=false)
        String id;

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

        public String getLink() {return this.link;}
        public void setLink(String value) {this.link = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

    }

    public static class KindleAsin {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class ReviewsCount {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class DescUserId {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Authors {

        @Element(name="author", required=false)
        Author author;

        public Author getAuthor() {return this.author;}
        public void setAuthor(Author value) {this.author = value;}

    }

    public static class Book {

        @Element(name="marketplace_id", required=false)
        MarketplaceId marketplaceId;

        @Element(name="friend_reviews", required=false)
        String friendReviews;

        @Element(name="book_links", required=false)
        BookLinks bookLinks;

        @Element(name="isbn", required=false)
        Isbn isbn;

        @Element(name="num_pages", required=false)
        String numPages;

        @Element(name="link", required=false)
        Link link;

        @Element(name="description", required=false)
        Description description;

        @Element(name="title", required=false)
        String title;

        @Element(name="language_code", required=false)
        String languageCode;

        @Element(name="small_image_url", required=false)
        SmallImageUrl smallImageUrl;

        @Element(name="text_reviews_count", required=false)
        TextReviewsCount textReviewsCount;

        @Element(name="series_works", required=false)
        SeriesWorks seriesWorks;

        @Element(name="is_ebook", required=false)
        String isEbook;

        @Element(name="edition_information", required=false)
        String editionInformation;

        @Element(name="id", required=false)
        String id;

        @Element(name="my_review", required=false)
        MyReview myReview;

        @Element(name="ratings_count", required=false)
        String ratingsCount;

        @Element(name="title_without_series", required=false)
        String titleWithoutSeries;

        @Element(name="reviews_widget", required=false)
        ReviewsWidget reviewsWidget;

        @Element(name="image_url", required=false)
        ImageUrl imageUrl;

        @Element(name="work", required=false)
        Work work;

        @Element(name="format", required=false)
        Format format;

        @ElementList(name="popular_shelves", required=false)
        List<Shelf> popularShelves;

        @Element(name="average_rating", required=false)
        String averageRating;

        @ElementList(name="buy_links", required=false)
        List<BuyLink> buyLinks;

        @Element(name="url", required=false)
        Url url;

        @Element(name="country_code", required=false)
        CountryCode countryCode;

        @Element(name="publication_month", required=false)
        String publicationMonth;

        @Element(name="publication_year", required=false)
        String publicationYear;

        @ElementList(name="similar_books", required=false)
        List<Book> similarBooks;

        @Element(name="isbn13", required=false)
        Isbn13 isbn13;

        @Element(name="publisher", required=false)
        String publisher;

        @Element(name="asin", required=false)
        String asin;

        @Element(name="publication_day", required=false)
        String publicationDay;

        @Element(name="kindle_asin", required=false)
        KindleAsin kindleAsin;

        @Element(name="authors", required=false)
        Authors authors;

        public MarketplaceId getMarketplaceId() {return this.marketplaceId;}
        public void setMarketplaceId(MarketplaceId value) {this.marketplaceId = value;}

        public String getFriendReviews() {return this.friendReviews;}
        public void setFriendReviews(String value) {this.friendReviews = value;}

        public BookLinks getBookLinks() {return this.bookLinks;}
        public void setBookLinks(BookLinks value) {this.bookLinks = value;}

        public Isbn getIsbn() {return this.isbn;}
        public void setIsbn(Isbn value) {this.isbn = value;}

        public String getNumPages() {return this.numPages;}
        public void setNumPages(String value) {this.numPages = value;}

        public Link getLink() {return this.link;}
        public void setLink(Link value) {this.link = value;}

        public Description getDescription() {return this.description;}
        public void setDescription(Description value) {this.description = value;}

        public String getTitle() {return this.title;}
        public void setTitle(String value) {this.title = value;}

        public String getLanguageCode() {return this.languageCode;}
        public void setLanguageCode(String value) {this.languageCode = value;}

        public SmallImageUrl getSmallImageUrl() {return this.smallImageUrl;}
        public void setSmallImageUrl(SmallImageUrl value) {this.smallImageUrl = value;}

        public TextReviewsCount getTextReviewsCount() {return this.textReviewsCount;}
        public void setTextReviewsCount(TextReviewsCount value) {this.textReviewsCount = value;}

        public SeriesWorks getSeriesWorks() {return this.seriesWorks;}
        public void setSeriesWorks(SeriesWorks value) {this.seriesWorks = value;}

        public String getIsEbook() {return this.isEbook;}
        public void setIsEbook(String value) {this.isEbook = value;}

        public String getEditionInformation() {return this.editionInformation;}
        public void setEditionInformation(String value) {this.editionInformation = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

        public MyReview getMyReview() {return this.myReview;}
        public void setMyReview(MyReview value) {this.myReview = value;}

        public String getRatingsCount() {return this.ratingsCount;}
        public void setRatingsCount(String value) {this.ratingsCount = value;}

        public String getTitleWithoutSeries() {return this.titleWithoutSeries;}
        public void setTitleWithoutSeries(String value) {this.titleWithoutSeries = value;}

        public ReviewsWidget getReviewsWidget() {return this.reviewsWidget;}
        public void setReviewsWidget(ReviewsWidget value) {this.reviewsWidget = value;}

        public ImageUrl getImageUrl() {return this.imageUrl;}
        public void setImageUrl(ImageUrl value) {this.imageUrl = value;}

        public Work getWork() {return this.work;}
        public void setWork(Work value) {this.work = value;}

        public Format getFormat() {return this.format;}
        public void setFormat(Format value) {this.format = value;}

        public List<Shelf> getPopularShelves() {return this.popularShelves;}
        public void setPopularShelves(List<Shelf> value) {this.popularShelves = value;}

        public String getAverageRating() {return this.averageRating;}
        public void setAverageRating(String value) {this.averageRating = value;}

        public List<BuyLink> getBuyLinks() {return this.buyLinks;}
        public void setBuyLinks(List<BuyLink> value) {this.buyLinks = value;}

        public Url getUrl() {return this.url;}
        public void setUrl(Url value) {this.url = value;}

        public CountryCode getCountryCode() {return this.countryCode;}
        public void setCountryCode(CountryCode value) {this.countryCode = value;}

        public String getPublicationMonth() {return this.publicationMonth;}
        public void setPublicationMonth(String value) {this.publicationMonth = value;}

        public String getPublicationYear() {return this.publicationYear;}
        public void setPublicationYear(String value) {this.publicationYear = value;}

        public List<Book> getSimilarBooks() {return this.similarBooks;}
        public void setSimilarBooks(List<Book> value) {this.similarBooks = value;}

        public Isbn13 getIsbn13() {return this.isbn13;}
        public void setIsbn13(Isbn13 value) {this.isbn13 = value;}

        public String getPublisher() {return this.publisher;}
        public void setPublisher(String value) {this.publisher = value;}

        public String getAsin() {return this.asin;}
        public void setAsin(String value) {this.asin = value;}

        public String getPublicationDay() {return this.publicationDay;}
        public void setPublicationDay(String value) {this.publicationDay = value;}

        public KindleAsin getKindleAsin() {return this.kindleAsin;}
        public void setKindleAsin(KindleAsin value) {this.kindleAsin = value;}

        public Authors getAuthors() {return this.authors;}
        public void setAuthors(Authors value) {this.authors = value;}

    }

    public static class Isbn {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class BuyLink {

        @Element(name="name", required=false)
        String name;

        @Element(name="link", required=false)
        String link;

        @Element(name="id", required=false)
        String id;

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

        public String getLink() {return this.link;}
        public void setLink(String value) {this.link = value;}

        public String getId() {return this.id;}
        public void setId(String value) {this.id = value;}

    }

    public static class Link {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Description {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class Title {

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

    public static class TextReviewsCount {

        @Text(required=false)
        String textValue;

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class SeriesWorks {

        @Text(required=false)
        String textValue;

        @Element(name="series_work", required=false)
        SeriesWork seriesWork;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public SeriesWork getSeriesWork() {return this.seriesWork;}
        public void setSeriesWork(SeriesWork value) {this.seriesWork = value;}

    }

    public static class SeriesWork {

        @Element(name="series", required=false)
        Series series;

        @Element(name="user_position", required=false)
        String userPosition;

        @Element(name="id", required=false)
        String id;

        public Series getSeries() {return this.series;}
        public void setSeries(Series value) {this.series = value;}

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

    public static class DefaultDescriptionLanguageCode {

        @Attribute(name="nil", required=false)
        Boolean nil;

        public Boolean getNil() {return this.nil;}
        public void setNil(Boolean value) {this.nil = value;}

    }

    public static class BooksCount {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class ReviewsWidget {

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

    public static class OriginalPublicationYear {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Url {

        @Element(name="#cdata-section", required=false)
        String cdataSection;

        public String getCdataSection() {return this.cdataSection;}
        public void setCdataSection(String value) {this.cdataSection = value;}

    }

    public static class BestBookId {

        @Text(required=false)
        String textValue;

        @Attribute(name="type", required=false)
        String type;

        public String getTextValue() {return this.textValue;}
        public void setTextValue(String value) {this.textValue = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

    public static class Series {

        @Element(name="note", required=false)
        String note;

        @Element(name="series_works_count", required=false)
        String seriesWorksCount;

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

    public static class DefaultChapteringBookId {

        @Attribute(name="nil", required=false)
        Boolean nil;

        @Attribute(name="type", required=false)
        String type;

        public Boolean getNil() {return this.nil;}
        public void setNil(Boolean value) {this.nil = value;}

        public String getType() {return this.type;}
        public void setType(String value) {this.type = value;}

    }

}