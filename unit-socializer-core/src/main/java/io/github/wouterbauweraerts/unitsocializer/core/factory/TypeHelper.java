package io.github.wouterbauweraerts.unitsocializer.core.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

import org.instancio.Instancio;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;

public class TypeHelper {
    public <T> Constructor<T> getConstructor(Class<T> type) {
        return (Constructor<T>) Arrays.stream(type.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new UnsupportedOperationException("No constructor found"));
    }

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

    public boolean isJavaType(Class<?> type) {
        return type.isPrimitive() || type.getPackage().getName().startsWith("java.");
    }

    public <T> T createJavaType(Class<T> javaType) {
        if (!isJavaType(javaType)) {
            throw new SociableTestException("Cannot create Java type. %s is not a Java type!".formatted(javaType.getCanonicalName()));
        }
        return Instancio.create(javaType);
    }
}
