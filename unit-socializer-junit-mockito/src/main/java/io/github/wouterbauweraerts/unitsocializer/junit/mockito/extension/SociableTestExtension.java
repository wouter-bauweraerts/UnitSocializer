package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfigReader;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;
import io.github.wouterbauweraerts.unitsocializer.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.JunitMockitoSociableTestInitializer;

/**
 * JUnit extension that integrates Mockito-based sociable testing functionality.
 * <p>
 * This extension manages the lifecycle of sociable tests by clearing the test context after each test execution
 * and handling the necessary preconditions before each test method runs.
 * </p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class SociableTestExtension implements BeforeEachCallback, AfterEachCallback {
    private final SociableTestContext sociableTestContext;
    private final BeforeEachCallbackHandler beforeEachCallbackHandler;

    /**
     * Constructs a new instance of the {@code SociableTestExtension}.
     * <p>
     * Initializes the sociable test context and sets up the before-each callback handler
     * using configuration loaded from the {@link MockingConfigReader}.
     * </p>
     */
    public SociableTestExtension() {
        sociableTestContext = SociableTestContext.getInstance();
        MockingConfig mockingConfig = JunitMockitoSociableTestInitializer.configReader().loadConfig();
        beforeEachCallbackHandler = JunitMockitoSociableTestInitializer.beforeEachCallbackHandler(sociableTestContext, mockingConfig);
    }

    /**
     * Clears the sociable test context after each test method execution.
     *
     * @param context the extension context for the current test method
     */
    @Override
    public void afterEach(ExtensionContext context) {
        sociableTestContext.clear();
    }

    /**
     * Handles the preconditions for the test execution by invoking the before-each callback handler.
     *
     * @param context the extension context for the current test method
     * @throws SociableTestException if the test class or test instance cannot be retrieved
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(() -> new SociableTestException("TestClass not found!"));
        Object testInstance = context.getTestInstance()
                .orElseThrow(() -> new SociableTestException("TestInstance not found!"));
    
        beforeEachCallbackHandler.beforeEach(testClass, testInstance);
    }


}
