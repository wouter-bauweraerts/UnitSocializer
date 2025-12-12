package io.github.wouterbauweraerts.unitsocializer.core.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.unitsocializer.core.factory.MockFactory;
import io.github.wouterbauweraerts.unitsocializer.core.factory.TypeHelper;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.TypeTokenSupplier;


/**
 * Helper class responsible for managing the instantiation of objects, utilizing mocks, type resolution,
 * and test context awareness during the creation of instances.
 *
 * <p>It works closely with {@link MockFactory} and {@link TypeHelper} to manage object instantiation
 * and enforce dependency resolution or mocking logic.</p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
@SuppressWarnings("unused")
public class InstanceHelper {
    private final MockFactory mockFactory;
    private final TypeResolver typeResolver;
    private final TypeHelper typeHelper;

    /**
     * Constructs an {@code InstanceHelper}.
     *
     * @param mockFactory  the factory used to create mock objects
     * @param typeResolver the resolver used to determine concrete types for abstract types
     * @param typeHelper   the helper utility for resolving constructors and constructing instances
     */
    public InstanceHelper(MockFactory mockFactory, TypeResolver typeResolver, TypeHelper typeHelper) {
        this.mockFactory = mockFactory;
        this.typeResolver = typeResolver;
        this.typeHelper = typeHelper;
    }

    /**
     * Returns the {@link TypeResolver} associated with this instance.
     *
     * @return the type resolver used for determining concrete types
     */
    public TypeResolver getTypeResolver() {
        return typeResolver;
    }

    /**
     * Creates an instance of the specified type.
     *
     * <p>If the type already exists in the {@link SociableTestContext}, the existing instance is returned.
     * Otherwise, this method creates an appropriate instance using the following logic:</p>
     * <ul>
     *     <li>If the type is a Java type, a native Java representation is created.</li>
     *     <li>If the type requires mocking, a mock instance is created using {@link MockFactory}.</li>
     *     <li>If the type is abstract, a concrete subtype is resolved and instantiated.</li>
     * </ul>
     *
     * <p>The created instance is added to the SociableTestContext to facilitate reuse.</p>
     *
     * @param <T>  the type of the object to be created
     * @param type the {@link Class} object representing the type to be created
     * @return the created instance of the specified type
     * @throws SociableTestInstantiationException if an error occurs during instantiation
     */
    public <T> T instantiate(Class<T> type) throws SociableTestInstantiationException {
        SociableTestContext instances = SociableTestContext.getInstance();
        Class<? extends T> typeToCreate = type;

        if (instances.exists(type)) {
            return instances.get(type);
        }

        if (typeHelper.isJavaType(type)) {
            return typeHelper.createJavaType(type);
        }

        if (mockFactory.shouldMock(type)) {
            return instances.putIfAbsent(
                    type,
                    mockFactory.mock(type)
            );
        }
        if (typeResolver.hasResolvedType(type)) {
            typeToCreate = typeResolver.resolve(type);
        } else if (typeResolver.isAbstract(type)) {
            typeToCreate = typeResolver.resolveType(type);
        }

        Constructor<? extends T> constructor = typeHelper.getConstructor(typeToCreate);

        Supplier<Object[]> paramResolver = () -> Arrays.stream(constructor.getGenericParameterTypes())
                .map(t -> {
                    if (t instanceof ParameterizedType pt) {
                        return this.instantiateGeneric(pt);
                    } else if (t instanceof Class<?> c) {
                        return this.instantiate(c);
                    } else {
                        return null;
                    }
                })
                .toArray();

        T instance = mockFactory.spy(typeHelper.createInstance(constructor, paramResolver));

        instances.putIfAbsent(type, instance);

        if (!typeToCreate.getSimpleName().equals(type.getSimpleName())) {
            instances.putIfAbsent(typeToCreate, instance);
        }

        return instance;
    }

    private Object instantiateGeneric(ParameterizedType pt) {
        SociableTestContext instances = SociableTestContext.getInstance();

        Class<?> typeClass = (Class<?>) pt.getRawType();
        Type[] typeArgs = pt.getActualTypeArguments();

        if (instances.exists(typeClass)) {
            return instances.get(typeClass);
        }

        if (typeHelper.isJavaType(typeClass) && !typeHelper.isCollection(typeClass)) {
            return typeHelper.createJavaType(Instancio.create(() -> pt));
        }

        if (mockFactory.shouldMock(typeClass)) {
            return instances.putIfAbsent(
                    pt.getRawType().getClass(),
                    mockFactory.mock(typeClass)
            );
        }

        if (typeHelper.isCollection(typeClass)) {
            return instantiateCollection(typeClass, typeArgs);
        }

        return null;
    }

    private Collection<?> instantiateCollection(Class<?> typeClass, Type[] typeArgs) {
        if (typeHelper.isList(typeClass)) {
            if (typeArgs.length > 1) {
                throw new SociableTestInstantiationException("Cannot instantiate a List with multiple generic types");
            }

            if (typeHelper.isJavaType(typeArgs[0].getClass())) {
                return Instancio.ofList(() -> typeArgs[0]).create();
            }
        }
        return null;
    }

    public void updateMockingConfig(MockingConfig config) {
        this.mockFactory.updateMockingConfig(config);
    }
}
