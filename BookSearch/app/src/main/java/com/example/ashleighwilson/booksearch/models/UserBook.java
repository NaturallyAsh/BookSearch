package com.example.ashleighwilson.booksearch.models;

import com.google.gson.Gson;

import org.simpleframework.xml.Element;

public class UserBook {
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
    Authors author;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static UserBook fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserBook.class);
    }

    public String getImageUrl() {return this.imageUrl;}
    public void setImageUrl(String value) {this.imageUrl = value;}

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

    public Authors getAuthor() {return this.author;}
    public void setAuthor(Authors value) {this.author = value;}


    /*@Element(required = false)
    String asin;

    @Element(required = false)
    long asin_updater_user_id;

    @Element(required = false)
    long author_id;

    @Element(required = false)
    long author_id_updater_user_id;

    @Element
    long id;

    @Element(required = false)
    String isbn;

    @Element(required = false)
    String isbn13;

    @Element(required = false)
    long image_updater_user_id;

    @Element(required = false)
    long isbn13_updater_user_id;

    @Element(required = false)
    long isbn_updater_user_id;

    @Element(required = false)
    String image_url;

    @Element(required = false)
    String small_image_url;

    @Element(required = false)
    String language_code;

    @Element(required = false)
    long language_updater_user_id;

    @Element(required = false)
    long num_pages;

    @Element(required = false)
    long num_pages_updater_user_id;

    @Element(required = false)
    String author_role;

    @Element(required = false)
    long author_role_updater_user_id;

    @Element(required = false)
    long book_authors_count;

    @Element(required = false)
    boolean is_ebook;

    @Element(required = false)
    String created_at;

    @Element(required = false)
    Work work;

    @Element(required = false)
    String description;

    @Element(required = false)
    String description_language_code;

    @Element(required = false)
    long description_updater_user_id;

    @Element(required = false)
    String edition_information;

    @Element(required = false)
    long edition_information_updater_user_id;

    @Element(required = false)
    String format;

    @Element(required = false)
    long format_updater_user_id;

    @Element(required = false)
    String image_uploaded_at;

    @Element(required = false)
    long publication_date_updater_user_id;

    @Element(required = false)
    long publication_day;

    @Element(required = false)
    String link;

    @ElementList(required = false)
    List<Author> author = new ArrayList<>();

    @Element(required = false)
    long publication_month;

    @Element(required = false)
    long publication_year;

    @Element(required = false)
    String publisher;

    @Element(required = false)
    String publisher_language_code;

    @Element(required = false)
    long publisher_updater_user_id;

    @Element(required = false)
    long ratings_count;

    @Element(required = false)
    long ratings_sum;

    @Element(required = false)
    float average_rating;

    @Element(required = false)
    long reviews_count;

    @Element(required = false)
    String s3_image_at;

    @ElementList(required = false)
    List<Review> friend_reviews = new ArrayList<>();

    @Element(required = false)
    String reviews_widget;

    @ElementList(required = false)
    List<Shelves> popular_shelves = new ArrayList<>();

    @ElementList(required = false)
    List<BookLink> book_links = new ArrayList<>();

    @ElementList(required = false)
    List<SeriesWork> series_works = new ArrayList<>();

    @ElementList(required = false)
    List<UserBook> similar_books = new ArrayList<>();

    @Element(required = false)
    String sort_by_title;

    @Element(required = false)
    String source_url;

    @Element(required = false)
    long text_reviews_count;

    @Element
    String title;

    @Element(required = false)
    String title_language_code;

    @Element(required = false)
    long title_updater_user_id;

    @Element(required = false)
    String updated_at;

    @Element(required = false)
    String url;

    @Element(required = false)
    long url_updater_user_id;

    @Element(required = false)
    long work_id;

    @Element(required = false)
    Author author;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getSmallImageUrl() {
        return small_image_url;
    }

    public String getOpenLibraryCoverImageUrl() {
        return String.format(
                "http://covers.openlibrary.org/b/isbn/%s-L.jpg",
                isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "asin='" + asin + '\'' +
                ", author=" + author +
                ", id=" + id +
                ", isbn='" + isbn + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", image_url='" + image_url + '\'' +
                ", small_image_url='" + small_image_url + '\'' +
                ", num_pages=" + num_pages +
                ", ratings_count=" + ratings_count +
                ", ratings_sum=" + ratings_sum +
                ", average_rating=" + average_rating +
                ", reviews_count=" + reviews_count +
                ", s3_image_at='" + s3_image_at + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Author getAuthor() {
        if (author != null) {
            return author;
        }

        if (author.size() == 0) {
            return null;
        }
        return author.get(0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Review> getFriendReviews() {
        return friend_reviews;
    }

    public long getReviewsCount() {
        return reviews_count;
    }

    public float getAverageRating() {
        return average_rating;
    }

    public String getFormat() {
        return format;
    }

    public long getNumPages() {
        return num_pages;
    }

    public Shelves getPopularShelve() {
        for (Shelves shelve: popular_shelves) {
            if (shelve.name.equals("to-read")) {
                continue;
            } else if (shelve.name.equals("currently-reading")) {
                continue;
            }

            return shelve;
        }

        return null;
    }

    public List<UserBook> getSimilarBooks() {
        return similar_books;
    }

    public String getPublisher() {
        return publisher;
    }

    public long getPublicationYear() {
        return publication_year;
    }

    public String getLink() {
        return link;
    }*/
}
