package com.aem.cloud.core.models.injectors.annotations;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@InjectAnnotation
@Source(TagProperty.SOURCE)
public @interface TagProperty {
    String name() default StringUtils.EMPTY;

    String SOURCE = "tag-property";

    InjectionStrategy injectionStrategy() default InjectionStrategy.DEFAULT;
}
