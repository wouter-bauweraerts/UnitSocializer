package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanInstantiateClassWithSimpleDependencyTest {
    @TestSubject
    DummyWithSimpleDependency dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();

        SimpleDummy dependency = dummy.simpleDummy();
        assertThat(dependency).isNotNull();
        assertThat(mockingDetails(dummy).isSpy()).isTrue();
    }

    public record DummyWithSimpleDependency(SimpleDummy simpleDummy) {
    }
}