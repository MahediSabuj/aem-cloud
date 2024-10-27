package com.aem.cloud.core.models.injectors;

import com.aem.cloud.core.models.injectors.annotations.TagProperty;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Component(service = Injector.class)
public class TagPropertyInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(TagPropertyInjector.class);

    @NotNull
    @Override
    public String getName() {
        return TagProperty.SOURCE;
    }

    @Nullable
    @Override
    public Object getValue(Object adaptable, String name, Type declaredType, AnnotatedElement element, DisposalCallbackRegistry callbackRegistry) {
        if (element.isAnnotationPresent(TagProperty.class)) {
            TagProperty annotation = element.getAnnotation(TagProperty.class);

            ResourceResolver resourceResolver = getResourceResolver(adaptable);
            if (resourceResolver == null) {
                logger.error("ResourceResolver is null, cannot inject tag property. " +
                        "Are you adapting from a resource or SlingHttpServletRequest?");
                return null;
            }

            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            if (tagManager == null) {
                logger.error("TagManager is null, cannot inject tag property. Are you adapting from a resource or SlingHttpServletRequest?");
                return null;
            }

            String key =  StringUtils.defaultIfEmpty(annotation.name(), name);
            final String[] tagKeys = getResource(adaptable).getValueMap().get(key, String[].class);
            if (tagKeys == null || tagKeys.length == 0){
                return null;
            }

            final Stream<Tag> tagStream = Arrays.stream(tagKeys).map(tagManager::resolve);
            return tagStream.filter(Objects::nonNull).findFirst().orElse(null);
        }

        return null;
    }

    private static ResourceResolver getResourceResolver(Object adaptable) {
        if (adaptable instanceof SlingHttpServletRequest) {
            return ((SlingHttpServletRequest) adaptable).getResourceResolver();
        }
        if (adaptable instanceof Resource) {
            return ((Resource) adaptable).getResourceResolver();
        }

        return null;
    }

    private static Resource getResource(Object adaptable) {
        if (adaptable instanceof SlingHttpServletRequest) {
            return ((SlingHttpServletRequest) adaptable).getResource();
        }
        if (adaptable instanceof Resource) {
            return (Resource) adaptable;
        }

        return null;
    }
}
