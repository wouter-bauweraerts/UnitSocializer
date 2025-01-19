package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface Resolve {
    Class<?> forClass();
    Class<?> use();
}
