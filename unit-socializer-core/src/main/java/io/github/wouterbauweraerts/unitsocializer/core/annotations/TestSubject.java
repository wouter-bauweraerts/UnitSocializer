package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to specify that the annotated field represents the test subject.
 * It is primarily used for dependency injection or contextual resolution
 * in sociable test scenarios.
 *
 * <p>The {@code typeResolvers} element allows specifying an array of
 * {@link Resolve} objects that define which implementation should be used for a certain type during test execution.</p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface TestSubject {
    
    /**
     * An array of {@link Resolve} objects that define the specific implementation
     * to use for certain types when this field is resolved in sociable test scenarios.
     *
     * <p>By default, this array is empty, meaning no specific resolvers are applied.</p>
     *
     * @return an array of {@code Resolve} objects
     */
    Resolve[] typeResolvers() default {};
}
