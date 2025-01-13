package io.github.wouterbauweraerts.sociabletesting.junit.mockito;

import io.github.wouterbauweraerts.sociabletesting.core.config.MockingConfig;
import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.extension.BeforeEachCallbackHandler;
import io.github.wouterbauweraerts.sociabletesting.core.factory.TypeHelper;
import io.github.wouterbauweraerts.sociabletesting.core.helpers.InstanceHelper;
import io.github.wouterbauweraerts.sociabletesting.core.helpers.TypeResolver;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.factory.MockitoMockFactory;

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
