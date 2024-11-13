package io.github.wouterbauweraerts.sociabletesting.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import io.github.wouterbauweraerts.sociabletesting.extension.SociableTestExtension;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(SociableTestExtension.class)
public @interface SociableTest {
}
