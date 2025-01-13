package io.github.wouterbauweraerts.sociabletesting.core.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;

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
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception occurred while instantiating test subject", e);

        }
    }
}
