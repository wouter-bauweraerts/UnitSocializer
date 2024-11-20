package io.github.wouterbauweraerts.sociabletesting.core.config.mapping;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class MockingConfigModule extends SimpleModule {

    public MockingConfigModule() {
        this.addDeserializer(Class.class, new StringToClassDeserializer());
    }
}
