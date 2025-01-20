package io.github.wouterbauweraerts.unitsocializer.junit.mockito.factory;

import org.mockito.Mockito;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.factory.MockFactory;

/**
 * Factory implementation for creating Mockito mocks and spies.
 * <p>
 * This class extends the {@link MockFactory} to provide mock and spy instances
 * using the Mockito framework.
 * </p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class MockitoMockFactory extends MockFactory {

    /**
     * Constructs a new {@code MockitoMockFactory} instance with the specified configuration.
     *
     * @param config the mocking configuration to use
     */
    public MockitoMockFactory(MockingConfig config) {
        super(config);
    }

    /**
     * Creates a mock instance of the given type using Mockito.
     *
     * @param <T>  the type of the mock
     * @param type the {@link Class} of the mock instance to be created
     * @return a mock instance of the specified type
     */
    @Override
    public <T> T mock(Class<T> type) {
        return Mockito.mock(type);
    }

    /**
     * Creates a spy (partial mock) of the given instance using Mockito.
     *
     * @param <T>      the type of the spied instance
     * @param instance the instance to be spied
     * @return a spy instance for the specified object
     */
    @Override
    public <T> T spy(T instance) {
        return Mockito.spy(instance);
    }
}
