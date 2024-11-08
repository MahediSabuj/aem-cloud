package com.aem.cloud.core.models;

import com.aem.cloud.core.models.injectors.annotations.ContentElement;
import com.aem.cloud.core.models.injectors.annotations.ContentFragment;
import org.apache.sling.models.annotations.Model;

import java.util.GregorianCalendar;

@Model(adaptables = { com.adobe.cq.dam.cfm.ContentFragment.class })
public class Article {
    @ContentElement
    String title;

    @ContentElement
    String topic;

    @ContentElement
    GregorianCalendar publishDate;

    @ContentFragment
    Author author;

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public GregorianCalendar getPublishDate() {
        return publishDate;
    }

    public Author getAuthor() {
        return author;
    }
}
