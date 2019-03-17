
package com.example.ashleighwilson.booksearch.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageLinks")
    @Expose
    private ImageLinks imageLinks;
    @SerializedName("industryIdentifiers")
    @Expose
    private List<IndustryIdentifier> industryIdentifiers;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("publishedDate")
    @Expose
    private String publishedDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The imageLinks
     */
    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    /**
     * 
     * @param imageLinks
     *     The imageLinks
     */
    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

}
