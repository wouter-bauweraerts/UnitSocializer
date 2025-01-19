package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.javatypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

@SociableTest
public class CanInstantiateClassWithPrimitiveTypeDependenciesTest {
    @TestSubject
    DummyWithPrimitiveTypes dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull()
                .hasNoNullFieldsOrProperties();
    }
}
