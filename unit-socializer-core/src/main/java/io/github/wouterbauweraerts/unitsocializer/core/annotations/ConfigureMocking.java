package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockConfigStrategy;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotation to configure mocking behavior for specified packages, annotations, and classes.
 * This annotation can be used at the class level to define which elements should be mocked
 * during test execution.
 *
 * @author Wouter Bauweraerts
 * @since 1.2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface ConfigureMocking {
    /**
     * Specifies packages whose classes should be mocked.
     *
     * @return array of package names to mock
     */
    String[] packages() default {};

    /**
     * Specifies which annotations should trigger mocking of the annotated classes.
     *
     * @return array of annotation classes that mark classes for mocking
     */
    Class<? extends Annotation>[] annotations() default {};

    /**
     * Specifies individual classes that should be mocked.
     *
     * @return array of classes to mock
     */
    Class<?>[] classes() default {};

    /**
     * Defines the strategy for handling multiple mock configurations.
     *
     * @return the {@link MockConfigStrategy} to use, defaults to OVERWRITE
     */
    MockConfigStrategy strategy() default MockConfigStrategy.REPLACE;
}
