package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

@SociableTest
class CanIntantiateClassWithSimpleDependencyTest {
    @TestSubject
    DummyWithSimpleDependency dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        Assertions.assertThat(dummy.simpleDummy()).isNotNull();
    }

    public record DummyWithSimpleDependency(SimpleDummy simpleDummy) {
    }
}