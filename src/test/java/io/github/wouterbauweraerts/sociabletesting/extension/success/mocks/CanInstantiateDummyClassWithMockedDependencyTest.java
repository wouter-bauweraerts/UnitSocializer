package io.github.wouterbauweraerts.sociabletesting.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.mocking.DummyClassToMock;

@SociableTest
class CanInstantiateDummyClassWithMockedDependencyTest {
    @TestSubject
    DummyWithClassToMock subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        assertThat(subject.toMock()).isNotNull();
        assertThat(mockingDetails(subject.toMock()).isMock()).isTrue();
    }

    public record DummyWithClassToMock(DummyClassToMock toMock) {
    }
}