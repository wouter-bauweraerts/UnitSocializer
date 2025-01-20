package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used to define a resolution strategy for a specific type.
 * <p>
 * This annotation allows developers to specify the resolution of one class
 * using another class, typically for dependency injection or replacement usage.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface Resolve {

    /**
     * Specifies the class type that this resolution applies to.
     *
     * @return the target class type that will be resolved.
     */
    Class<?> forClass();

    /**
     * Specifies the class type to be used for resolution.
     *
     * @return the class type that will replace or be used for the resolution.
     */
    Class<?> use();
}
