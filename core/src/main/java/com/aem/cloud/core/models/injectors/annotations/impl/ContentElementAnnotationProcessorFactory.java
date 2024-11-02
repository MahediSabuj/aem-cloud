package com.aem.cloud.core.models.injectors.annotations.impl;

import com.aem.cloud.core.models.injectors.annotations.ContentElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.injectorspecific.AbstractInjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.StaticInjectAnnotationProcessorFactory;
import org.osgi.service.component.annotations.Component;

import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

@Component(service = StaticInjectAnnotationProcessorFactory.class)
public class ContentElementAnnotationProcessorFactory implements StaticInjectAnnotationProcessorFactory {
    @Override
    public InjectAnnotationProcessor2 createAnnotationProcessor(AnnotatedElement element) {
        return Optional.ofNullable(
            element.getAnnotation(ContentElement.class)
        ).map(ContentElementAnnotationProcessorFactory.PagePropertyAnnotationProcessor::new)
        .orElse(null);
    }

    private static class PagePropertyAnnotationProcessor extends AbstractInjectAnnotationProcessor2 {
        private final ContentElement annotation;

        public PagePropertyAnnotationProcessor(ContentElement annotation) {
            this.annotation = annotation;
        }

        @Override
        public String getName() {
            return StringUtils.isBlank(annotation.name()) ? null : annotation.name();
        }

        @Override
        public InjectionStrategy getInjectionStrategy() {
            return annotation.injectionStrategy();
        }
    }
}
