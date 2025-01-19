package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.javatypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DummyWithWrapperTypes(
        Boolean aBoolean,
        Byte aByte,
        Character aChar,
        Short aShort,
        Integer aPrimitiveInt,
        Long aLong,
        Float aFloat,
        Double aDouble
) {
}
