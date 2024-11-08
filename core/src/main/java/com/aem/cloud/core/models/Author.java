package com.aem.cloud.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.cloud.core.models.injectors.annotations.ContentElement;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { ContentFragment.class })
public class Author {
    @ContentElement
    String name;

    private String getName() {
        return name;
    }
}
