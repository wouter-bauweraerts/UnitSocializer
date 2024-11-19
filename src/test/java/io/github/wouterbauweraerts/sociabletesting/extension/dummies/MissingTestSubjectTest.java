package io.github.wouterbauweraerts.sociabletesting.extension.dummies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;

@SociableTest
@Tag("dummyTest")
public class MissingTestSubjectTest {
    @Test
    void shouldFail() {
        assertThat(true).isTrue();
    }
}
