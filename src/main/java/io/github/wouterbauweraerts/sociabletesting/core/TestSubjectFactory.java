package io.github.wouterbauweraerts.sociabletesting.core;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfigReader;

public class TestSubjectFactory {
    private static final MockingConfig CONFIG = MockingConfigReader.loadConfig();

    private TestSubjectFactory () {
        // Utility class should not be instantiated
    }

    private static <T> boolean shouldMock(Class<T> type) {
        boolean annotatedWithOneOfAnnotationsToBeMocked = CONFIG.annotations().stream()
                .anyMatch(a -> Arrays.stream(type.getAnnotations())
                        .anyMatch(classAnnotation -> classAnnotation.annotationType().equals(a))
                );
        boolean typeShouldBeMocked = CONFIG.classes().contains(type);
        boolean packageShouldBeMocked = CONFIG.packages().contains(type.getName());

        return annotatedWithOneOfAnnotationsToBeMocked || typeShouldBeMocked || packageShouldBeMocked;
    }

    public static <T> T instantiate(Class<T> type) throws SociableTestInstantiationException {
        if (shouldMock(type)) {
            return mock(type);
        }

        Constructor<T> constructor = (Constructor<T>) Arrays.stream(type.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new UnsupportedOperationException("No constructor found"));

        try {
            return switch (constructor.getParameterCount()) {
                case 0 -> constructor.newInstance();
                default -> constructor.newInstance(
                        Arrays.stream(constructor.getParameterTypes())
                            .map(TestSubjectFactory::instantiate)
                            .toArray()
                );
            };
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception occurred while instantiating test subject", e);
        }
    }
}
