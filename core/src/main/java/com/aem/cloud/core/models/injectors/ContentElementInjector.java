package com.aem.cloud.core.models.injectors;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.aem.cloud.core.models.injectors.annotations.ContentElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component(service = Injector.class)
public class ContentElementInjector implements Injector {
    @NotNull
    @Override
    public String getName() {
        return ContentElement.SOURCE;
    }

    @Nullable
    @Override
    public Object getValue(Object adaptable, String name, Type declaredType, AnnotatedElement element, DisposalCallbackRegistry callbackRegistry) {
        if (element.isAnnotationPresent(ContentElement.class)) {
            ContentElement annotation = element.getAnnotation(ContentElement.class);

            ContentFragment contentFragment = (ContentFragment) adaptable;
            if (contentFragment != null) {
                String key =  StringUtils.defaultIfEmpty(annotation.name(), name);
                com.adobe.cq.dam.cfm.ContentElement contentElement = contentFragment.getElement(key);
                if (contentElement != null) {
                    FragmentData fragmentData = contentElement.getValue();
                    if (fragmentData != null) {
                        return fragmentData.getValue();
                    }
                }

            }
        }
        return null;
    }
}
