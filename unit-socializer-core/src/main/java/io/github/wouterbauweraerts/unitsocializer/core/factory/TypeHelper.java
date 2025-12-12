package io.github.wouterbauweraerts.unitsocializer.core.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import org.instancio.Instancio;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;


/**
 * A utility class that provides helper methods for working with types and constructors.
 * This class simplifies tasks like finding constructors, creating instances of types,
 * and checking if a type belongs to the Java standard library.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class TypeHelper {
    /**
     * Retrieves the constructor of the given class with the highest number of parameters.
     *
     * @param type the class whose constructor is to be retrieved
     * @param <T>  the type of the class
     * @return the constructor with the maximum number of parameters
     * @throws UnsupportedOperationException if no constructor is found
     */
    public <T> Constructor<T> getConstructor(Class<T> type) {
        return (Constructor<T>) Arrays.stream(type.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new UnsupportedOperationException("No constructor found"));
    }

    /**
     * Creates an instance of the given constructor, resolving parameters as required.
     *
     * @param constructor   the constructor to use for instance creation
     * @param paramResolver a supplier providing the parameters required for the constructor
     * @param <T>           the type of the instance to create
     * @return the created instance of the specified type
     * @throws SociableTestInstantiationException if any error occurs during instantiation
     */
    public <T> T createInstance(Constructor<T> constructor, Supplier<Object[]> paramResolver) {
        try {
            return switch (constructor.getParameterCount()) {
                case 0 -> constructor.newInstance();
                default -> constructor.newInstance(
                        paramResolver.get()
                );
            };
        } catch (SociableTestException ste) {
            throw ste;
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception occurred while instantiating test subject", e);
        }
    }

    /**
     * Checks if the given class is a primitive type or belongs to the Java standard library.
     *
     * @param type the class to check
     * @return {@code true} if the class is a Java type, {@code false} otherwise
     */
    public boolean isJavaType(Class<?> type) {
        return type.isPrimitive() || type.getPackage().getName().startsWith("java.");
    }

    /**
     * Creates an instance of the specified Java type using Instancio.
     *
     * @param javaType the Java type to create
     * @param <T>      the type of the object to create
     * @return an instance of the specified Java type
     * @throws SociableTestException if the specified type is not a valid Java type
     */
    public <T> T createJavaType(Class<T> javaType) {
        if (!isJavaType(javaType)) {
            throw new SociableTestException("Cannot create Java type. %s is not a Java type!".formatted(javaType.getCanonicalName()));
        }
        return Instancio.create(javaType);
    }

    public boolean isCollection(Class<?> typeClass) {
        return Collection.class.isAssignableFrom(typeClass);
    }

    public boolean isList(Class<?> typeClass) {
        return List.class.isAssignableFrom(typeClass);
    }
}
