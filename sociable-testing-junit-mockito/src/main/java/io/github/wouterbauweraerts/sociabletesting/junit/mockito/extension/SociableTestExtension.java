package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfigReader;
import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.sociabletesting.core.factory.InstanceFactory;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.factory.MockitoMockFactory;

public class SociableTestExtension implements BeforeEachCallback, AfterEachCallback {

    private final SociableTestContext sociableTestContext;
    private final BeforeEachCallbackHandler beforeEachCallbackHandler;

    public SociableTestExtension() {
        sociableTestContext = SociableTestContext.getInstance();
        MockingConfig mockingConfig = MockingConfigReader.loadConfig();
        InstanceFactory instanceFactory = new InstanceFactory(mockingConfig, new MockitoMockFactory(mockingConfig));
        beforeEachCallbackHandler = new BeforeEachCallbackHandler(sociableTestContext, instanceFactory);
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
