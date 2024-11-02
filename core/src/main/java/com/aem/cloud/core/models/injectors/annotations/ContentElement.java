package com.aem.cloud.core.models.injectors.annotations;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@InjectAnnotation
@Source(ContentElement.SOURCE)
public @interface ContentElement {
    String name() default StringUtils.EMPTY;

    String SOURCE = "content-element";

    InjectionStrategy injectionStrategy() default InjectionStrategy.DEFAULT;
}
