package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.javatypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DummyWithBuiltInTypes(
        String aString,
        LocalDate aLocalDate,
        LocalTime aLocalTime,
        LocalDateTime aLocalDateTime,
        BigDecimal aBigDecimal,
        BigInteger aBigInteger
) {
}
