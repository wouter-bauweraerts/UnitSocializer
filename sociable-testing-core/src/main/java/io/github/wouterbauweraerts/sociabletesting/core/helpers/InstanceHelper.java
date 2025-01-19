package io.github.wouterbauweraerts.sociabletesting.core.helpers;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.function.Supplier;

import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.sociabletesting.core.factory.MockFactory;
import io.github.wouterbauweraerts.sociabletesting.core.factory.TypeHelper;

@SuppressWarnings("unused")
public class InstanceHelper {
    private final MockFactory mockFactory;
    private final TypeResolver typeResolver;
    private final TypeHelper typeHelper;

    public InstanceHelper(MockFactory mockFactory, TypeResolver typeResolver, TypeHelper typeHelper) {
        this.mockFactory = mockFactory;
        this.typeResolver = typeResolver;
        this.typeHelper = typeHelper;
    }

    public <T> T instantiate(Class<T> type) throws SociableTestInstantiationException {
        SociableTestContext instances = SociableTestContext.getInstance();
        Class<? extends T> typeToCreate = type;

        if (instances.exists(type)) {
            return instances.get(type);
        }

        if (typeHelper.isJavaType(type)) {
            // Create instance
            // Return without adding to the map
            return typeHelper.createJavaType(type);
        }

        if (mockFactory.shouldMock(type)) {
            return instances.putIfAbsent(
                    type,
                    mockFactory.mock(type)
            );
        }

        if (typeResolver.isAbstract(type)) {
            typeToCreate = typeResolver.resolveType(type);
        }

        Constructor<? extends T> constructor = typeHelper.getConstructor(typeToCreate);

        Supplier<Object[]> paramResolver = () -> Arrays.stream(constructor.getParameterTypes())
                .map(this::instantiate)
                .toArray();

        T instance = typeHelper.createInstance(constructor, paramResolver);

        instances.putIfAbsent(type, instance);

        if (!typeToCreate.getSimpleName().equals(type.getSimpleName())) {
            instances.putIfAbsent(typeToCreate, instance);
        }

        return instance;
    }
}
