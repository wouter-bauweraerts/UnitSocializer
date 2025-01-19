package io.github.wouterbauweraerts.unitsocializer.core.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.BuiltInTypesSource;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClass;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterface;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;

class TypeHelperTest {
    TypeHelper helper = new TypeHelper();

    @ParameterizedTest
    @BuiltInTypesSource
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

    @ParameterizedTest
    @BuiltInTypesSource
    void createJavaType_givenJavaType_createsRandomInstance(Class<?> type) {
        assertThat(helper.createJavaType(type)).isNotNull()
                .isInstanceOf(ClassUtils.primitiveToWrapper(type));
    }

    @ParameterizedTest
    @ValueSource(classes = {
            DummyInterface.class,
            DummyAbstractClass.class
    })
    void createJavaType_givenNonJavaType_throwsExpected(Class<?> type) {
        String msg = "Cannot create Java type. %s is not a Java type!".formatted(type.getCanonicalName());

        assertThatThrownBy(() -> helper.createJavaType(type))
                .isInstanceOf(SociableTestException.class)
                .hasMessage(msg);
    }
}