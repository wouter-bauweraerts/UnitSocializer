package io.github.wouterbauweraerts.unitsocializer.junit.mockito;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfig;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import io.github.wouterbauweraerts.unitsocializer.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.unitsocializer.core.factory.TypeHelper;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.InstanceHelper;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.TypeResolver;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.factory.MockitoMockFactory;

public class JunitMockitoSociableTestInitializer {
    private JunitMockitoSociableTestInitializer() {
        // This is a utility class. Should not be instantiated
    }

    private static InstanceHelper instanceFactory(MockingConfig config) {
        return new InstanceHelper(
                new MockitoMockFactory(config),
                new TypeResolver(),
                new TypeHelper()
        );
    }

    public static BeforeEachCallbackHandler beforeEachCallbackHandler(SociableTestContext ctx, MockingConfig config) {
        return new BeforeEachCallbackHandler(
                ctx,
                instanceFactory(config)
        );
    }
}
