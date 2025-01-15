package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.abstractclasses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.interfaces.DependsOnDummyWithMultipleImplementations;

@SociableTest
@Tag("dummyTest")
public class CanNotInstantiateAbstractClassWithMultipleImplementationTest {
    @TestSubject
    DependsOnAbstractClassWIthMultipleImplementations subject;

    @Test
    void shouldFail() {
        assertThat(true).isTrue();
    }
}
