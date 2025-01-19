package io.github.wouterbauweraerts.unitsocializer.core.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.params.provider.ValueSource;

@ValueSource(classes = {
        boolean.class,
        byte.class,
        char.class,
        double.class,
        float.class,
        int.class,
        long.class,
        short.class,
        Boolean.class,
        Byte.class,
        Character.class,
        Double.class,
        Float.class,
        Integer.class,
        Long.class,
        Short.class,
        String.class,
        BigDecimal.class,
        BigInteger.class,
        LocalDateTime.class,
        LocalDate.class,
        LocalTime.class,
})
@Retention(RUNTIME)
@Target(METHOD)
public @interface BuiltInTypesSource {
}
