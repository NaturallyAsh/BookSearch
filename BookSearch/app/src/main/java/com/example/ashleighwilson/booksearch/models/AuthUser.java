package com.example.ashleighwilson.booksearch.models;

import android.util.Log;

import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Author;

@Root
public class AuthUser {

    private static final String TAG = AuthUser.class.getSimpleName();

    @Attribute(required = false, name = "id")
    long id_attribute = -1;

    @Element(required = false, name = "id")
    long id_element = -1;

    @Element
    String name;

    @Element
    String link;

    @Element(required = false)
    String location;

    @Element(required = false)
    String image_url;

    @Element(required = false)
    String small_image_url;

    @Element(required = false)
    String user_name;

    @Element(required = false)
    boolean is_following;

    @Element(required = false)
    String about;

    @Element(required = false)
    String age;

    @Element(required = false)
    String gender;

    @Element(required = false)
    String joined;

    @Element(required = false)
    String last_active;

    @Element(required = false)
    String website;

    @Element(required = false)
    String interests;

    @Element(required = false)
    String favorite_books;

    //@ElementList(required = false)
    //List<Author> favorite_authors = new ArrayList<>();

    @Element(required = false)
    boolean friend;

    @Element(required = false)
    String friend_status;

    @Element(required = false)
    String updates_rss_url;

    @Element(required = false)
    String reviews_rss_url;

    @Element(required = false)
    long friends_count;

    @Element(required = false)
    long groups_count;

    @Element(required = false)
    long reviews_count;

    /*@ElementList(required = false)
    List<UserShelf> user_shelves = new ArrayList<>();

    @ElementList(required = false)
    List<Update> updates = new ArrayList<>();*/

    @Element(required = false)
    String user_statuses;

    public AuthUser() {
        Injector.getInstance().inject(this);
    }

    public long getId() {
        if (id_attribute != -1) {
            return id_attribute;
        } else if (id_element != -1) {
            return id_element;
        } else {
            return -1;
        }
    }

    public String getName(){return name;}

    public String getImage_url() {
        if (image_url != null) {
            return image_url;
        }
        return null;
    }

    public String getLink(){return link;}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static AuthUser fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AuthUser.class);
    }

    @Override
    public String toString() {
        return "User{" +
                "id_attribute=" + id_attribute +
                ", id_element=" + id_element +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
