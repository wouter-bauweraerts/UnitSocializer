package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;

@SociableTest
class DummyWithClassToMockTest {
    @TestSubject
    DummyWithClassToMock subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        assertThat(subject.getToMock()).isNotNull();
        assertThat(mockingDetails(subject.getToMock()).isMock()).isTrue();
    }
}