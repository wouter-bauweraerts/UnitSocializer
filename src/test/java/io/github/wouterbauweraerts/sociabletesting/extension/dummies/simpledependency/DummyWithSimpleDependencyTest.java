package io.github.wouterbauweraerts.sociabletesting.extension.dummies.simpledependency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;

@SociableTest
class DummyWithSimpleDependencyTest {
    @TestSubject
    DummyWithSimpleDependency dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        assertThat(dummy.getSimpleDummy()).isNotNull();
    }
}