package io.github.wouterbauweraerts.sociabletesting.core.extension.success;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.SimpleDummy;

@SociableTest
class CanInstantiateSimpleDummyWithoutDependenciesTest {
    @TestSubject
    SimpleDummy simpleDummy;

    @Test
    void test() {
        assertThat(simpleDummy).isNotNull();
    }
}
