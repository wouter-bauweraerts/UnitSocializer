package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.javatypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanInstantiateClassWithBuiltInTypeDependenciesTest {
    @TestSubject
    DummyWithBuiltInTypes dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull()
                .hasNoNullFieldsOrProperties();
    }
}
