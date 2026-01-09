package io.github.wouterbauweraerts.unitsocializer.core.helpers;

import static java.util.Objects.isNull;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.*;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.classpath.ClasspathScanner;
import io.github.wouterbauweraerts.unitsocializer.core.util.Pair;


/**
 * A utility class designed to resolve implementations for abstract classes, interfaces or uninstantiated classes.
 * It caches resolved types for efficient lookup and provides methods to locate implementations
 * dynamically at runtime using reflection.
 *
 * Classes that are configured with {@link io.github.wouterbauweraerts.unitsocializer.core.annotations.Resolve} are stored in the typeResolverCache
 *
 * If a type is not preconfigured, we will try to resolve the type to use with classpath scanning. We should try to avoid this if possible,
 * because classpath scanning is an expensive operation, which slows down our test execution.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class TypeResolver {
    private final Map<Class<?>, Class<?>> typeResolverCache;

    /**
     * Constructs a new {@code TypeResolver} initializing the cache for resolved types.
     */
    public TypeResolver() {
        typeResolverCache = new HashMap<>();
    }

    /**
     * Checks if there is already a resolved type for the specified abstract type in the cache.
     *
     * @param abstractType the abstract type to check for resolution
     * @return {@code true} if the type is resolved, {@code false} otherwise
     */
    public boolean hasResolvedType(Class<?> abstractType) {
        return typeResolverCache.containsKey(abstractType);
    }

    /**
     * Resolves the specified type using the cached type resolution mappings.
     *
     * @param <T> the type to resolve
     * @param typeToResolve the class object representing the type to resolve
     * @return the resolved class if present in the cache, or {@code null} if not found
     */
    public <T> Class<? extends T> resolve(Class<T> typeToResolve) {
        return  (Class<? extends T>) typeResolverCache.get(typeToResolve);
    }

    /**
     * Adds a mapping between an abstract type and its concrete implementation
     * to the cache.
     *
     * @param forClass the abstract type to be resolved
     * @param useClass the concrete implementation to use for the abstract type
     */
    public void addResolver(Class<?> forClass, Class<?> useClass) {
        typeResolverCache.put(forClass, useClass);
    }

    /**
     * Checks if the given class is either an interface or an abstract class.
     *
     * @param clazz the class to check
     * @return {@code true} if the class is abstract or an interface, {@code false} otherwise
     */
    public boolean isAbstract(Class<?> clazz) {
        int classModifiers = clazz.getModifiers();
        return clazz.isInterface() || Modifier.isInterface(classModifiers) || Modifier.isAbstract(classModifiers);
    }

    /**
     * Resolves the implementation type for the given abstract type.
     * If the type is found in the cache, it is returned. Otherwise, all implementations
     * of the abstract type are located and filtered using classpath scanning.
     * If there is exactly one implementation, it is resolved and returned; otherwise, an exception is thrown.
     *
     * @param <T> the type to resolve
     * @param abstractType the abstract class or interface to resolve implementations for
     * @return the resolved concrete implementation class
     * @throws SociableTestException if no implementations or multiple implementations are found
     */
    public <T> Class<? extends T> resolveType(Class<T> abstractType) {
        if (hasResolvedType(abstractType)) {
            return (resolve(abstractType));
        }

        List<Class<?>> implementations = new ClasspathScanner().findImplementations(abstractType)
                .stream()
                .filter(c -> !this.isAbstract(c))
                .toList();
        if (isNull(implementations) || implementations.isEmpty()) {
            throw SociableTestException.noImplementations(abstractType.getSimpleName());
        }

        if (implementations.size() > 1) {
            throw SociableTestException.multipleImplementations(abstractType.getSimpleName());
        }

        return (Class<? extends T>) implementations.getFirst();
    }

    public <T> List<Class<?>> resolveAll(Class<T> clazz) {
        List<Class<?>> resolved = new ArrayList<>();

        if (hasResolvedType(clazz)) {
            resolved.add(resolve(clazz));
        }

        if (this.isAbstract(clazz)) {
            List<Class<T>> implementations = ClasspathScanner.INSTANCE.findImplementations(clazz)
                    .stream()
                    .filter(c -> !this.isAbstract(c))
                    .filter(clazz::isAssignableFrom)
                    .map(c -> (Class<T>) c)
                    .toList();
            resolved.addAll(implementations);
        } else {
            resolved.add(clazz);
        }


        if (resolved.isEmpty()) {
            throw SociableTestException.noImplementations(clazz.getSimpleName());
        }

        return resolved;
    }

    public Class<?> resolveParameterized(Type rawType, Type[] actualTypeArguments) {
        Class<?> rawClass = (Class<?>) rawType;

        return ClasspathScanner.INSTANCE.findImplementations(rawClass).stream()
                .map(it -> Pair.of(it, it.getGenericInterfaces()))
                .filter(it -> Arrays.stream(it.second()).allMatch(t -> t instanceof ParameterizedType))
                .filter(args -> {
                    if (Arrays.stream(actualTypeArguments).allMatch(arg -> arg instanceof WildcardType)) {
                        return true;
                    }
                    return Arrays.stream(args.second())
                            .filter(it -> it instanceof ParameterizedType)
                            .map(it -> (ParameterizedType) it)
                            .map(ParameterizedType::getActualTypeArguments)
                            .anyMatch(it -> Arrays.stream(it).allMatch(ta -> Arrays.stream(actualTypeArguments).allMatch(actTa -> actTa.equals(ta))));
                })
                .map(Pair::first)
                .findFirst().orElseThrow();
    }
}
