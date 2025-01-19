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

public class SociableTestExtension implements BeforeEachCallback, AfterEachCallback {

    private final SociableTestContext sociableTestContext;
    private final BeforeEachCallbackHandler beforeEachCallbackHandler;

    public SociableTestExtension() {
        sociableTestContext = SociableTestContext.getInstance();
        MockingConfig mockingConfig = MockingConfigReader.loadConfig();
        beforeEachCallbackHandler = JunitMockitoSociableTestInitializer.beforeEachCallbackHandler(sociableTestContext, mockingConfig);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        sociableTestContext.clear();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(() -> new SociableTestException("TestClass not found!"));
        Object testInstance = context.getTestInstance()
                .orElseThrow(() -> new SociableTestException("TestInstance not found!"));


        beforeEachCallbackHandler.beforeEach(testClass, testInstance);
    }


}
