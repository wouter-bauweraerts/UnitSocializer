package io.github.wouterbauweraerts.sociabletesting.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.core.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.mocking.mockpackage.AmazingClass;

@SociableTest
class CanInstantiateDummyWithClassFromMockPackageTest {
    @TestSubject
    DummyWithClassFromMockPackage subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        assertThat(subject.toMock()).isNotNull();
        assertThat(mockingDetails(subject.toMock()).isMock()).isTrue();
    }

    public record DummyWithClassFromMockPackage(AmazingClass toMock) {
    }
}