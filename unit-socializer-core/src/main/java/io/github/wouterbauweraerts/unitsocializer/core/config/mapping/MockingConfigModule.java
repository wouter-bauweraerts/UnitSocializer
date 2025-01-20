package io.github.wouterbauweraerts.unitsocializer.core.config.mapping;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A custom configuration module for mocking in the application. 
 * This class extends Jackson's {@link SimpleModule} to provide 
 * custom deserialization logic using {@link StringToClassDeserializer}.
 *
 * @author Wouter Bauweraerts
 * @version 0.0.1
 * @since nov 20 2024
 */
public class MockingConfigModule extends SimpleModule {

    /**
     * Constructs a {@code MockingConfigModule} and registers a custom 
     * deserializer for converting strings to {@link Class} objects 
     * using {@link StringToClassDeserializer}.
     */
    public MockingConfigModule() {
        this.addDeserializer(Class.class, new StringToClassDeserializer());
    }
}
