package io.github.wouterbauweraerts.unitsocializer.core.config;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Stream;


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
 * @since 0.0.1
 */
public record MockingConfig(
        List<Class<? extends Annotation>> annotations,
        List<Class<?>> classes,
        List<String> packages
) {
    public static MockingConfig merge(MockingConfig first, MockingConfig second) {
        return new MockingConfig(
                mergeUnique(first.annotations, second.annotations),
                mergeUnique(first.classes, second.classes),
                mergeUnique(first.packages, second.packages)
        );
    }

    private static <T> List<T> mergeUnique(List<T> first, List<T> second) {
        return Stream.concat(first.stream(), second.stream())
                .distinct()
                .toList();
    }
}
