package io.github.wouterbauweraerts.unitsocializer.core.annotations;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The {@code Predefined} annotation can be applied to fields to indicate
 * that the field value must be used to resolve fields of the given type.
 * 
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Predefined {
}
