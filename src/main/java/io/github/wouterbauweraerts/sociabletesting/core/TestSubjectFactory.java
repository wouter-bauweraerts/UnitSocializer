package io.github.wouterbauweraerts.sociabletesting.core;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

public class TestSubjectFactory {
    private static final MockingConfig CONFIG = MockingConfigReader.loadConfig();

    private TestSubjectFactory () {
        // Utility class should not be instantiated
    }

    private static <T> boolean shouldMock(Class<T> type) {
        // Class is annototated with one of the given types
        boolean annotatedWithOneOfAnnotationsToBeMocked = CONFIG.annotationsToMock().contains(type.getName());
        // Class is in types to be mocked
        boolean typeShouldBeMocked = CONFIG.annotationsToMock().contains(type.getName());

        return annotatedWithOneOfAnnotationsToBeMocked || typeShouldBeMocked;
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
            throw new SociableTestInstantiationException("Exception occured while instantiating test subject", e);
        }
    }
}
