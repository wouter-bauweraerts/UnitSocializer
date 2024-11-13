package io.github.wouterbauweraerts.sociabletesting.extension;

import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SociableTestExtension implements BeforeEachCallback {
    private static final Logger LOGGER = Logger.getLogger(SociableTestExtension.class.getName());
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        LOGGER.info(context.toString());
        LOGGER.info(context.getTestClass().get().getSimpleName());
    }
}
