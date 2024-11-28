package io.github.wouterbauweraerts.sociabletesting.core.config;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.sociabletesting.core.config.mapping.MockingConfigModule;

public class MockingConfigReader {
    private static final String DEFAULT_RESOURCE_NAME = "sociable-testing-defaults.yaml";
    private static final String RESOURCE_NAME = "sociable-testing-config.yaml";

    private static final ObjectReader yamlMapper = new ObjectMapper(new YAMLFactory())
            .registerModule(new MockingConfigModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .readerFor(MockingConfig.class)
            .withRootName("sociable-test");

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
                return new MockingConfig(emptyList(), emptyList(), emptyList());
            }
                return filterNulls(yamlMapper.readValue(config, MockingConfig.class));
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception while loading config", e);
        }

    }

    private static MockingConfig filterNulls(MockingConfig original) {
        return new MockingConfig(
                ofNullable(original.annotations()).orElse(emptyList()).stream()
                        .filter(Objects::nonNull)
                        .toList(),
                ofNullable(original.classes()).orElse(emptyList()).stream()
                        .filter(Objects::nonNull)
                        .toList(),
                ofNullable(original.packages()).orElse(emptyList()).stream()
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
