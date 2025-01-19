package io.github.wouterbauweraerts.unitsocializer.junit.mockito.factory;

import org.mockito.Mockito;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.factory.MockFactory;

public class MockitoMockFactory extends MockFactory {

    public MockitoMockFactory(MockingConfig config) {
        super(config);
    }

    @Override
    public <T> T mock(Class<T> type) {
        return Mockito.mock(type);
    }

    @Override
    public <T> T spy(T isntance) {
        return Mockito.spy(isntance);
    }
}
