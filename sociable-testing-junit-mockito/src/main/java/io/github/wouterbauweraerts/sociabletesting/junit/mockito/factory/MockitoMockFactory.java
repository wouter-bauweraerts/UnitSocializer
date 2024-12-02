package io.github.wouterbauweraerts.sociabletesting.junit.mockito.factory;

import org.mockito.Mockito;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.factory.MockFactory;

public class MockitoMockFactory extends MockFactory {

    public MockitoMockFactory(MockingConfig config) {
        super(config);
    }

    @Override
    public <T> T mock(Class<T> type) {
        return Mockito.mock(type);
    }
}
