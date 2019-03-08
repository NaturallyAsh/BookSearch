package com.example.ashleighwilson.booksearch.models;

import org.simpleframework.xml.Attribute;

public class Description {

    @Attribute(name="nil", required=false)
    Boolean nil;

    public Boolean getNil() {return this.nil;}
    public void setNil(Boolean value) {this.nil = value;}
}
