package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.mockpackage.AmazingClass;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanInstantiateDummyWithClassFromMockPackageTest {
    @TestSubject
    DummyWithClassFromMockPackage subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        Assertions.assertThat(subject.toMock()).isNotNull();
        assertThat(mockingDetails(subject.toMock()).isMock()).isTrue();
    }

    public record DummyWithClassFromMockPackage(AmazingClass toMock) {
    }
}