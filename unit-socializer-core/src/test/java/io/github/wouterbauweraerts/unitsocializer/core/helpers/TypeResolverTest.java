package io.github.wouterbauweraerts.unitsocializer.core.helpers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClass;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassImpl;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassNoImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterface;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceImpl;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceNoImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;

class TypeResolverTest {
    TypeResolver helper = new TypeResolver();

    @ParameterizedTest(name = "isAbstract({0}) should return {1}")
    @MethodSource("isAbstractSource")
    void isAbstract(Class<?> clazz, boolean expected) {
        assertThat(helper.isAbstract(clazz)).isEqualTo(expected);
    }

    static Stream<Arguments> isAbstractSource() {
        return Stream.of(
                Arguments.of(String.class, false),
                Arguments.of(Object.class, false),
                Arguments.of(SimpleDummy.class, false),
                Arguments.of(DummyInterface.class, true),
                Arguments.of(DummyAbstractClass.class, true)
        );
    }

    @ParameterizedTest
    @ValueSource(classes = {String.class, Object.class, SimpleDummy.class})
    void findImplementations_typeNotAbstract_throwsExpected(Class<?> concreteType) {
        String msg = "Unable to determine type to instantiate for type %s. Not abstract".formatted(concreteType.getSimpleName());

        assertThatThrownBy(() -> helper.resolveType(concreteType))
                .isInstanceOf(SociableTestException.class)
                .hasMessage(msg);
    }

    @ParameterizedTest
    @ValueSource( classes = {DummyAbstractClassNoImplementations.class, DummyInterfaceNoImplementations.class})
    void resolveType_noImplementationsFound_throwsExpected(Class<?> typeToInstantiate) {
        String msg = "Unable to determine type to instantiate for abstract type %s. No implementations".formatted(typeToInstantiate.getSimpleName());

        assertThatThrownBy(() -> helper.resolveType(typeToInstantiate))
                .isInstanceOf(SociableTestException.class)
                .hasMessage(msg);
    }

    @ParameterizedTest
    @ValueSource( classes = {DummyAbstractClassMultipleImplementations.class, DummyInterfaceMultipleImplementations.class})
    void resolveType_multipleImplementationsFound_throwsExpected(Class<?> typeToInstantiate) {
        String msg = "Unable to determine type to instantiate for abstract type %s. Multiple implementations".formatted(typeToInstantiate.getSimpleName());

        assertThatThrownBy(() -> helper.resolveType(typeToInstantiate))
                .isInstanceOf(SociableTestException.class)
                .hasMessage(msg);
    }

    @ParameterizedTest
    @MethodSource("singleImplementationSource")
    void resolveType_singleImplementationFound(Class<?> typeToInstantiate, Class<?> expectedType) {
        assertThat(helper.resolveType(typeToInstantiate)).isEqualTo(expectedType);
    }

    static Stream<Arguments> singleImplementationSource() {
        return Stream.of(
                Arguments.of(DummyAbstractClass.class, DummyAbstractClassImpl.class),
                Arguments.of(DummyInterface.class, DummyInterfaceImpl.class)
        );
    }
}