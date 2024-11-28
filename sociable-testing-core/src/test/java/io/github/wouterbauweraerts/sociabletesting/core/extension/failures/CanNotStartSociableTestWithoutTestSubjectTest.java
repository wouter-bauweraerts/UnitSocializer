package io.github.wouterbauweraerts.sociabletesting.core.extension.failures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.SociableTest;


@SociableTest
@Tag("dummyTest")
class CanNotStartSociableTestWithoutTestSubjectTest {
    @Test
    void shouldFail() {
        assertThat(true).isTrue();
    }
}
