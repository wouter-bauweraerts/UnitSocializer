package io.github.wouterbauweraerts.unitsocializer.core.config;

/**
 * Enum defining strategies for handling mock configurations when multiple configurations exist.
 *
 * @author Wouter Bauweraerts
 * @since 1.2.0
 */
public enum MockConfigStrategy {
    /**
     * Merges multiple mock configurations together by combining their annotations, classes and packages
     * while maintaining uniqueness.
     */
    MERGE,

    /**
     * Overwrites any existing mock configuration with the new configuration, discarding the old one.
     */
    OVERWRITE
}
