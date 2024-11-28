package io.github.wouterbauweraerts.sociabletesting.extension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.sociabletesting.core.extension.SociableTestExtension;
import io.github.wouterbauweraerts.sociabletesting.core.state.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.util.ReflectionUtil;

@ExtendWith(MockitoExtension.class)
class SociableTestExtensionTest {

    @InjectMocks
    SociableTestExtension extension;
    @Mock
    SociableTestContext ctx;
    @Mock
    BeforeEachCallbackHandler callbackHandler;

    @Mock
    ExtensionContext extensionContext;

    @BeforeEach
    void setUp() throws Exception {
        extension = new SociableTestExtension();

        ReflectionUtil.setFieldValue(SociableTestExtension.class.getDeclaredField("sociableTestContext"), extension, ctx);
        ReflectionUtil.setFieldValue(SociableTestExtension.class.getDeclaredField("beforeEachCallbackHandler"), extension, callbackHandler);
    }

    @Test
    void afterEach_clearsContext() {
        extension.afterEach(extensionContext);

        verify(ctx).clear();
    }

    @Test
    void beforeEach_testClassNotFound() {
        when(extensionContext.getTestClass()).thenReturn(Optional.empty());

        assertThatCode(() -> extension.beforeEach(extensionContext))
                .isInstanceOf(SociableTestException.class)
                .hasMessage("TestClass not found!");
    }

    @Test
    void beforeEach_testInstanceNotFound() {
        when(extensionContext.getTestClass()).thenReturn(Optional.of(String.class));
        when(extensionContext.getTestInstance()).thenReturn(Optional.empty());

        assertThatCode(() -> extension.beforeEach(extensionContext))
                .isInstanceOf(SociableTestException.class)
                .hasMessage("TestInstance not found!");
    }

    @Test
    void beforeEach() {
        Class<String> testClass = String.class;
        Object testInstance = new Object();

        when(extensionContext.getTestClass()).thenReturn(Optional.of(testClass));
        when(extensionContext.getTestInstance()).thenReturn(Optional.of(testInstance));

        assertThatCode(() -> extension.beforeEach(extensionContext))
                .doesNotThrowAnyException();

        verify(callbackHandler).beforeEach(testClass, testInstance);
    }
}