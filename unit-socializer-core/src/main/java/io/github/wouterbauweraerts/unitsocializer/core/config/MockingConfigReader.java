package io.github.wouterbauweraerts.unitsocializer.core.config;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.wouterbauweraerts.unitsocializer.core.config.mapping.MockingConfigModule;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;


/**
 * Utility class for loading the mocking configuration from YAML files.
 * It handles the deserialization and fallback mechanisms for default configurations.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class MockingConfigReader {
    /**
     * Name of the file containing the default configuration values.
     * This file is provided by the library and can be overwritten by adding your own configuration in unit-socializer-config.yaml
     */
    private static final String DEFAULT_RESOURCE_NAME = "unit-socializer-defaults.yaml";
    /**
     * Name of the primary configuration file that is attempted to load first.
     * This file contains the configuration that is provided by the user.
     */
    private static final String RESOURCE_NAME = "unit-socializer-config.yaml";

    /**
     * YAML mapper configured for deserializing mocking configurations.
     * Uses the {@link MockingConfigModule} module for custom behaviors and
     * excludes null values during serialization.
     */
    private static final ObjectReader yamlMapper = new ObjectMapper(new YAMLFactory())
            .registerModule(new MockingConfigModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .readerFor(MockingConfig.class)
            .withRootName("unit-socializer");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MockingConfigReader() {
        // Utility class should not be instantiated
    }

    /**
     * Loads the mocking configuration from either the primary or default YAML file.
     * If the primary configuration is unavailable, it falls back to the default file.
     *
     * @return a {@link MockingConfig} instance with the loaded configurations,
     *         or an empty configuration if the file is empty.
     * @throws SociableTestInstantiationException if none of the configuration files are found
     *         or an error occurs during file reading or parsing.
     */
    public static MockingConfig loadConfig() {
        ClassLoader classLoader = MockingConfigReader.class.getClassLoader();
        InputStream config = classLoader.getResourceAsStream(RESOURCE_NAME);
        if (Objects.isNull(config)) {
            config = classLoader.getResourceAsStream(DEFAULT_RESOURCE_NAME);
        }
    
        if (Objects.isNull(config)) {
            throw new SociableTestInstantiationException("Could not find configuration!");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(config))) {
            String configurationContent = br.lines().collect(Collectors.joining("\n"));
            if (configurationContent.isEmpty()) {
                return new MockingConfig(emptyList(), emptyList(), emptyList());
            }
            return filterNulls(yamlMapper.readValue(configurationContent, MockingConfig.class));
        } catch (Exception e) {
            throw new SociableTestInstantiationException("Exception while loading config", e);
        }
    }

    /**
     * Filters out null values from the lists in the provided {@link MockingConfig}.
     *
     * @param original the original {@link MockingConfig} object to be filtered.
     * @return a new {@link MockingConfig} instance with null values removed from all lists.
     */
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
