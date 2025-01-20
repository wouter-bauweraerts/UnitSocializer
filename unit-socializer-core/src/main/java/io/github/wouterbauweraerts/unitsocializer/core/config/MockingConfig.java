package io.github.wouterbauweraerts.unitsocializer.core.config;

import java.lang.annotation.Annotation;
import java.util.List;


/**
 * A record representing the configuration for mocking elements within a framework.
 * This configuration specifies annotations, classes, and packages that indicate
 * which elements should be mocked.
 *
 * @param annotations A list of annotation types used to identify mockable elements.
 * @param classes     A list of specific classes that should be mocked.
 * @param packages    A list of package names indicating the scope of mocking.
 *
 * @author Wouter Bauweraerts
 * @version 0.0.1
 * @since nov 20 2024
 */
public record MockingConfig(
        List<Class<? extends Annotation>> annotations,
        List<Class<?>> classes,
        List<String> packages
) {
}
