package io.github.wouterbauweraerts.sociabletesting.extension.success;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.SimpleDummy;

@SociableTest
class CanIntantiateClassWithSimpleDependencyTest {
    @TestSubject
    DummyWithSimpleDependency dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        assertThat(dummy.simpleDummy()).isNotNull();
    }

    public record DummyWithSimpleDependency(SimpleDummy simpleDummy) {
    }
}