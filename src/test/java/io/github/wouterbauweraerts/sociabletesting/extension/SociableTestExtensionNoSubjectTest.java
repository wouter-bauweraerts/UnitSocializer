package io.github.wouterbauweraerts.sociabletesting.extension;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;

@SociableTest
class SociableTestExtensionNoSubjectTest {
    @Test
    void shouldFail() {
        assertThat(true).isTrue();
    }
}
