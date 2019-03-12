package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

public class TextReviewsCount {

    @Text(required=false)
    String textValue;

    @Attribute(name="type", required=false)
    String type;

    public String getTextValue() {return this.textValue;}
    public void setTextValue(String value) {this.textValue = value;}

    public String getType() {return this.type;}
    public void setType(String value) {this.type = value;}
}
