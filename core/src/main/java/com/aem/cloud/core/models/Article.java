package com.aem.cloud.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.cloud.core.models.injectors.annotations.ContentElement;
import org.apache.sling.models.annotations.Model;

import java.util.GregorianCalendar;

@Model(adaptables = { ContentFragment.class })
public class Article {
    @ContentElement
    String title;

    @ContentElement
    String topic;

    @ContentElement
    GregorianCalendar publishDate;

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public GregorianCalendar getPublishDate() {
        return publishDate;
    }
}
