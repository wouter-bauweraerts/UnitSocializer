package io.github.wouterbauweraerts.unitsocializer.core.config.mapping;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class MockingConfigModule extends SimpleModule {

    public MockingConfigModule() {
        this.addDeserializer(Class.class, new StringToClassDeserializer());
    }
}
