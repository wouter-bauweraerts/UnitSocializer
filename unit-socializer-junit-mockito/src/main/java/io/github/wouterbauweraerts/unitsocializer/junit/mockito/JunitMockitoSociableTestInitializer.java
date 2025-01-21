package io.github.wouterbauweraerts.unitsocializer.junit.mockito;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfigReader;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import io.github.wouterbauweraerts.unitsocializer.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.unitsocializer.core.factory.TypeHelper;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.InstanceHelper;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.TypeResolver;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.factory.MockitoMockFactory;

/**
 * Utility class providing helper methods to initialize sociable test configurations for
 * JUnit tests leveraging Mockito.
 *
 * <p>This class cannot be instantiated as it only provides static methods.</p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class JunitMockitoSociableTestInitializer {
    private static final String DEFAULT_CONFIG_FILE_NAME = "unit-socializer-defaults-junit-mockito.yaml";

    private JunitMockitoSociableTestInitializer() {
        // This is a utility class. Should not be instantiated
    }

    /**
     * Creates an instance of {@link InstanceHelper} using the provided mocking configuration.
     *
     * @param config the mocking configuration used to create the instance
     * @return an {@code InstanceHelper} configured with the given mocking settings
     */
    private static InstanceHelper instanceFactory(MockingConfig config) {
        return new InstanceHelper(
                new MockitoMockFactory(config),
                new TypeResolver(),
                new TypeHelper()
        );
    }

    /**
     * Creates a {@link BeforeEachCallbackHandler} configured with the given sociable test
     * context and mocking configuration.
     *
     * @param ctx the sociable test context to be used
     * @param config the mocking configuration used to initialize the callback handler
     * @return a {@code BeforeEachCallbackHandler} for the given context and configuration
     */
    public static BeforeEachCallbackHandler beforeEachCallbackHandler(SociableTestContext ctx, MockingConfig config) {
        return new BeforeEachCallbackHandler(
                ctx,
                instanceFactory(config)
        );
    }

    /**
     * Creates an {@link MockingConfigReader} with the desired default config filename.
     *
     * @return an {@code MocingConfigReader} for loading mocking configuration with fallback to default configuration file
     */
    public static MockingConfigReader configReader() {
        return new MockingConfigReader(DEFAULT_CONFIG_FILE_NAME);
    }
}
