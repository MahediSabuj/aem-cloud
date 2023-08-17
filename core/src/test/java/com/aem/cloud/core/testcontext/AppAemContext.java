package com.aem.cloud.core.testcontext;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;

import org.apache.sling.testing.mock.sling.ResourceResolverType;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextCallback;

/**
 * Sets up {@link AemContext} for unit tests in this application.
 */
public final class AppAemContext {
    private AppAemContext() {
        // static methods only
    }

    /**
     * @return {@link AemContext}
     */
    public static AemContext newAemContext() {
        return newAemContextBuilder().build();
    }

    /**
     * @return {@link AemContextBuilder}
     */
    public static AemContextBuilder newAemContextBuilder() {
        return newAemContextBuilder(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    /**
     * @return {@link AemContextBuilder}
     */
    public static AemContextBuilder newAemContextBuilder(ResourceResolverType resourceResolverType) {
        return new AemContextBuilder()
             .plugin(CACONFIG)
             .plugin(CORE_COMPONENTS)
             .afterSetUp(SETUP_CALLBACK);
    }

    /**
     * Custom set up rules required in all unit tests.
     */
    private static final AemContextCallback SETUP_CALLBACK = new AemContextCallback() {
        @Override
        public void execute(AemContext context) {
            // custom project initialization code for every unit test
        }
    };
}
