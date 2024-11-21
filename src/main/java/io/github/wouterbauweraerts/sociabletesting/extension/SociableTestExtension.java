package io.github.wouterbauweraerts.sociabletesting.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.state.SociableTestContext;

public class SociableTestExtension implements BeforeEachCallback, AfterEachCallback {

    private final SociableTestContext sociableTestContext;
    private final BeforeEachCallbackHandler beforeEachCallbackHandler;

    public SociableTestExtension() {
        sociableTestContext = SociableTestContext.getInstance();
        beforeEachCallbackHandler = BeforeEachCallbackHandler.getInstance();
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
