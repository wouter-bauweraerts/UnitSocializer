package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.failures.abstractclasses;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
@Tag("dummyTest")
public class CanNotInstantiateAbstractClassWithMultipleImplementationTest {
    @TestSubject
    DependsOnAbstractClassWIthMultipleImplementations subject;

    @Test
    void shouldFail() {
        fail("Did not expect to start test");
    }
}
