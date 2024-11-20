package io.github.wouterbauweraerts.sociabletesting.core;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfigReader;

public class TestSubjectFactory {
    private static final MockingConfig CONFIG = MockingConfigReader.loadConfig();

    private TestSubjectFactory () {
        // Utility class should not be instantiated
    }

    private static <T> boolean shouldMock(Class<T> type) {
        boolean annotatedWithOneOfAnnotationsToBeMocked = CONFIG.annotations().contains(type.getName());
        boolean typeShouldBeMocked = CONFIG.classes().contains(type.getName());
        boolean packageShouldBeMocked = CONFIG.packages().contains(type.getName());

        return annotatedWithOneOfAnnotationsToBeMocked || typeShouldBeMocked || packageShouldBeMocked;
    }

    public static <T> T instantiate(Class<T> type) throws SociableTestInstantiationException {
        if (shouldMock(type)) {
            return null;
//            return mock(type);
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
