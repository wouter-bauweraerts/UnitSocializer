package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.mocking.DummyClassToMock;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

@SociableTest
class CanInstantiateDummyClassWithMockedDependencyTest {
    @TestSubject
    DummyWithClassToMock subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        Assertions.assertThat(subject.toMock()).isNotNull();
        assertThat(mockingDetails(subject.toMock()).isMock()).isTrue();
    }

    public record DummyWithClassToMock(DummyClassToMock toMock) {
    }
}