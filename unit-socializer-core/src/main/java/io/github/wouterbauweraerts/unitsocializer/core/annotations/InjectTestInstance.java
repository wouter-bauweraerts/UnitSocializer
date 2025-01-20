package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to mark a field that should be injected in a test instance.
 * <p>
 * This annotation can be applied to fields, and it indicates that the field
 * will receive an instance of an instance created for the test execution.
 * </p>
 *
 * <p>
 * Injecting test instances allows us to verify behavior and apply stubbings if necessary
 * </p>
 *
 * @author Wouter Bauweraerts
 * @version 0.0.1
 * @since nov 21 2024
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectTestInstance {
}
