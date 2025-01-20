package io.github.wouterbauweraerts.unitsocializer.core.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A singleton context used for managing instances during testing.
 * Stores and retrieves instances based on their class types.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class SociableTestContext {
    private static final SociableTestContext INSTANCE = new SociableTestContext();

    private final Map<Class<?>, Object> instances = new HashMap<>();

    /**
     * Returns the singleton instance of the SociableTestContext.
     *
     * @return the singleton instance
     */
    public static SociableTestContext getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor to prevent external instantiation.
     * Ensures the use of a singleton design pattern.
     */
    private SociableTestContext() {
        // No need for logic. Add private constructor to hide default constructor
    }

    /**
     * Clears all stored instances within the context.
     */
    public void clear() {
        instances.clear();
    }

    /**
     * Stores the given instance under its class type if one is not already present.
     *
     * @param clazz the class type of the instance
     * @param instance the instance to store
     * @param <T> the type of the instance
     * @return the existing instance if it was already present, or the provided instance otherwise
     */
    public <T> T putIfAbsent(Class<?> clazz, T instance) {
        T existingInstance = (T)instances.putIfAbsent(clazz, instance);
        return Objects.nonNull(existingInstance) ? existingInstance : instance;
    }

    /**
     * Retrieves the stored instance of the specified class type.
     *
     * @param clazz the class type of the instance to retrieve
     * @param <T> the type of the instance
     * @return the stored instance of the specified class type
     * @throws IllegalStateException if no instance of the class type is present
     */
    public <T> T get(Class<T> clazz) {
        if (!instances.containsKey(clazz)) {
            throw new IllegalStateException("No instance found of " + clazz);
        }
        return (T)instances.get(clazz);
    }

    /**
     * Checks if an instance of the specified class type exists within the context.
     *
     * @param type the class type to check for
     * @param <T> the type of the instance
     * @return {@code true} if an instance of the specified class type is present, {@code false} otherwise
     */
    public <T> boolean exists(Class<T> type) {
        return instances.containsKey(type);
    }
}
