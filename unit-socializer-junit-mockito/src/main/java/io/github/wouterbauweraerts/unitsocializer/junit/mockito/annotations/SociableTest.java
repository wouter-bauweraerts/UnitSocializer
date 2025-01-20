package io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.SociableTestExtension;

/**
 * Meta-annotation to indicate that a test class contains sociable unit tests.
 * <p>
 * This annotation registers the {@link SociableTestExtension} jUnit extension to enable specific
 * test functionality for sociable testing scenarios.
 * </p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(SociableTestExtension.class)
public @interface SociableTest {
}
