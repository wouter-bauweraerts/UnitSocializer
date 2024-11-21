package io.github.wouterbauweraerts.sociabletesting.core.state;

import java.util.HashMap;
import java.util.Map;

public class SociableTestContext {
    private static final SociableTestContext INSTANCE = new SociableTestContext();

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public static SociableTestContext getInstance() {
        return INSTANCE;
    }

    private SociableTestContext() {

    }

    public void clear() {
        instances.clear();
    }

    public <T> T putIfAbsent(Class<T> clazz, T instance) {
        return (T)instances.putIfAbsent(clazz, instance);
    }

    public <T> T get(Class<T> clazz) {
        if (!instances.containsKey(clazz)) {
            throw new IllegalStateException("No instance found of " + clazz);
        }
        return (T)instances.get(clazz);
    }

    public <T> boolean exists(Class<T> type) {
        return instances.containsKey(type);
    }
}
