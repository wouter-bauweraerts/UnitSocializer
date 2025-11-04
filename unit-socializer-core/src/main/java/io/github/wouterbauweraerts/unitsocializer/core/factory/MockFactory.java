package io.github.wouterbauweraerts.unitsocializer.core.factory;

import java.util.Arrays;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;


/**
 * Abstract factory responsible for creating mock and spy instances based on a given configuration.
 * <p>
 * This class utilizes the {@link MockingConfig} object to determine whether a class, its annotations,
 * or its package qualify the class for mocking. Subclasses must implement the methods to 
 * create mock and spy objects.
 *
 * Implementations will be provided for the specific mocking library in use
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public abstract class MockFactory {
    /**
     * Configuration settings for mocking behavior used by the factory.
     */
    private final MockingConfig config;

    /**
     * Constructs a MockFactory with the provided mocking configuration.
     * 
     * @param config the {@link MockingConfig} containing the mock-related configuration data
     */
    protected MockFactory(MockingConfig config) {
        this.config = config;
    }

    /**
     * Determines whether the given type should be mocked.
     * <p>
     * A type is considered suitable for mocking if it is:
     * <ul>
     *   <li>Annotated with one of the annotations specified in the configuration</li>
     *   <li>Explicitly listed in the configuration's classes</li>
     *   <li>Part of a package listed in the configuration's packages</li>
     * </ul>
     * 
     * @param <T>  the type of the class to be checked
     * @param type the {@link Class} object representing the type to be checked
     * @return {@code true} if the type should be mocked; {@code false} otherwise
     */
    public <T> boolean shouldMock(Class<T> type) {
        boolean annotatedWithOneOfAnnotationsToBeMocked = config.annotations().stream()
                .anyMatch(a -> Arrays.stream(type.getAnnotations())
                        .anyMatch(classAnnotation -> classAnnotation.annotationType().equals(a))
                );
        boolean typeShouldBeMocked = config.classes().contains(type);
        boolean packageShouldBeMocked = config.packages().contains(type.getPackageName());
    
        return annotatedWithOneOfAnnotationsToBeMocked || typeShouldBeMocked || packageShouldBeMocked;
    }

    /**
     * Creates a mock instance of the specified type.
     * 
     * @param <T>  the type of the object to be mocked
     * @param type the {@link Class} of the object to be mocked
     * @return a mock instance of the given type
     */
    public abstract <T> T mock(Class<T> type);
    /**
     * Creates a spy for the given instance.
     * <p>
     * A spy is a partial mock that can track interactions with 
     * real methods, while allowing stubbing of selected methods.
     * 
     * @param <T>     the type of the instance to be spied on
     * @param instance the instance to create a spy for
     * @return a spy instance based on the provided object
     */
    public abstract <T> T spy(T instance);
}
