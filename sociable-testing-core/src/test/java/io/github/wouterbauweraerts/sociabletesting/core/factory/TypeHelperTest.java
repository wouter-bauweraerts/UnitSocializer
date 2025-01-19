package io.github.wouterbauweraerts.sociabletesting.core.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClass;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterface;

class TypeHelperTest {
    TypeHelper helper = new TypeHelper();

    @ParameterizedTest
    @ValueSource(classes = {
            boolean.class,
            byte.class,
            char.class,
            double.class,
            float.class,
            int.class,
            long.class,
            short.class,
            void.class,
            Boolean.class,
            Byte.class,
            Character.class,
            Double.class,
            Float.class,
            Integer.class,
            Long.class,
            Short.class,
            String.class,
            Void.class,
            BigDecimal.class,
            BigInteger.class,
            LocalDateTime.class,
            LocalDate.class,
            LocalTime.class,
    })
    void isJavaType_returnsTrueForJavaType(Class<?> type) {
        assertThat(helper.isJavaType(type)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(classes = {
            DummyInterface.class,
            DummyAbstractClass.class
    })
    void isJavaType_returnsFalseForNonJavaClass(Class<?> type) {
        assertThat(helper.isJavaType(type)).isFalse();
    }
}