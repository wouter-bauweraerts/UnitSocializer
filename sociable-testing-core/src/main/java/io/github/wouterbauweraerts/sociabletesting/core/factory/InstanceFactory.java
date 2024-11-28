package io.github.wouterbauweraerts.sociabletesting.core.factory;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;

public class InstanceFactory {
    private final MockingConfig config;
    private final MockFactory mockFactory;

    public InstanceFactory(MockingConfig config, MockFactory mockFactory) {
        this.config = config;
        this.mockFactory = mockFactory;
    }

    public <T> T instantiate(Class<T> type) throws SociableTestInstantiationException {
        SociableTestContext instances = SociableTestContext.getInstance();

        if (instances.exists(type)) {
            return instances.get(type);
        }

        if (mockFactory.shouldMock(type)) {
            T mock = mock(type);
            instances.putIfAbsent(type, mock);
            return mock;
        }

        Constructor<T> constructor = (Constructor<T>) Arrays.stream(type.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new UnsupportedOperationException("No constructor found"));

        try {
            T instance = switch (constructor.getParameterCount()) {
                case 0 -> constructor.newInstance();
                default -> constructor.newInstance(
                        Arrays.stream(constructor.getParameterTypes())
                            .map(this::instantiate)
                            .toArray()
                );
            };

            instances.putIfAbsent(type, instance);
            return instance;
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception occurred while instantiating test subject", e);
        }
    }
}
