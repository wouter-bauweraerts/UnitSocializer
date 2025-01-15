package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;


@SociableTest
@Tag("dummyTest")
class CanNotStartSociableTestWithoutTestSubjectTest {
    @Test
    void shouldFail() {
        fail("Did not expect to start test");
    }
}
