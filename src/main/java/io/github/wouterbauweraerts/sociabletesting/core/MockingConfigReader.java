package io.github.wouterbauweraerts.sociabletesting.core;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class MockingConfigReader {
    private static final String DEFAULT_RESOURCE_NAME = "sociable-testing-defaults.yaml";
    private static final String RESOURCE_NAME = "sociable-testing-config.yaml";

    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    private MockingConfigReader() {
        // Utility class should not be instantiated
    }

    public static MockingConfig loadConfig() {
        ClassLoader classLoader = MockingConfigReader.class.getClassLoader();
        URL config = classLoader.getResource(RESOURCE_NAME);
        if (Objects.isNull(config)) {
            config = classLoader.getResource(DEFAULT_RESOURCE_NAME);
        }

        if (Objects.isNull(config)) {
            throw new SociableTestInstantiationException("Could not find configuration!");
        }


        try {
            if (Files.size(Paths.get(config.toURI())) == 0) {
                return new MockingConfig(emptyList(), emptyList());
            }
                return yamlMapper.readValue(config, MockingConfig.class);
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception while loading config", e);
        }

    }
}
