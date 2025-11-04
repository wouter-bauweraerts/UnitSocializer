package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

@SociableTest
class CanInstantiateClassWithNestedClassTest {
    @TestSubject
    DummyWithSimpleDependency dummy;

    @Nested
    class NestedClassTest {
        @Test
        void test() {
            assertThat(dummy).isNotNull();

            SimpleDummy dependency = dummy.simpleDummy();
            assertThat(dependency).isNotNull();
            assertThat(mockingDetails(dummy).isSpy()).isTrue();
        }
    }

    public record DummyWithSimpleDependency(SimpleDummy simpleDummy) {
    }
}