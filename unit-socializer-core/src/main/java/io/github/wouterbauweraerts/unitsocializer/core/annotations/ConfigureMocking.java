package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ConfigureMocking {
    String[] packages() default {};
    Class<? extends Annotation>[] annotations() default {};
    Class<?>[] classes() default {};
}
