package io.github.wouterbauweraerts.sociabletesting.core.factory;

import java.util.Arrays;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;

public class MockFactory {
    private final MockingConfig config;

    public MockFactory(MockingConfig config) {
        this.config = config;
    }

    public <T> boolean shouldMock(Class<T> type) {
        boolean annotatedWithOneOfAnnotationsToBeMocked = config.annotations().stream()
                .anyMatch(a -> Arrays.stream(type.getAnnotations())
                        .anyMatch(classAnnotation -> classAnnotation.annotationType().equals(a))
                );
        boolean typeShouldBeMocked = config.classes().contains(type);
        boolean packageShouldBeMocked = config.packages().contains(type.getPackageName());

        return annotatedWithOneOfAnnotationsToBeMocked || typeShouldBeMocked || packageShouldBeMocked;
    }
}
