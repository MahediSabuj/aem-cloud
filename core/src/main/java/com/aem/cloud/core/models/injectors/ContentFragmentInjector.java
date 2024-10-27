package com.aem.cloud.core.models.injectors;

import com.aem.cloud.core.models.injectors.annotations.ContentFragment;
import com.aem.cloud.core.utils.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
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

@Component(service = Injector.class)
public class ContentFragmentInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(ContentFragmentInjector.class);

    @NotNull
    @Override
    public String getName() {
        return ContentFragment.SOURCE;
    }

    @Nullable
    @Override
    public Object getValue(Object adaptable, String name, Type declaredType, AnnotatedElement element, DisposalCallbackRegistry callbackRegistry) {
        if (element.isAnnotationPresent(ContentFragment.class)) {
            ContentFragment annotation = element.getAnnotation(ContentFragment.class);

            ResourceResolver resourceResolver = ResourceUtil.getResourceResolver(adaptable);
            if (resourceResolver != null) {
                Resource resource = ResourceUtil.getResource(adaptable);
                if (resource != null) {
                    String key =  StringUtils.defaultIfEmpty(annotation.name(), name);
                    final String fragmentPath = resource.getValueMap().get(key, String.class);
                    if (StringUtils.isNotBlank(fragmentPath)) {
                        Resource fragmentResource = resourceResolver.getResource(fragmentPath);
                        if (fragmentResource != null) {
                            com.adobe.cq.dam.cfm.ContentFragment fragment = resource.adaptTo(com.adobe.cq.dam.cfm.ContentFragment.class);
                            if (fragment != null) {
                                
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
