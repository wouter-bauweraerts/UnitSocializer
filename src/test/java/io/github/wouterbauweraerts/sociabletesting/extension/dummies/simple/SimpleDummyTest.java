package io.github.wouterbauweraerts.sociabletesting.extension.dummies.simple;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;

@SociableTest
class SimpleDummyTest {
    @TestSubject
    SimpleDummy simpleDummy;

    @Test
    void test() {
        assertThat(simpleDummy).isNotNull();
    }
}
