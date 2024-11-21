package io.github.wouterbauweraerts.sociabletesting.extension.success;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.demo.dummies.mocking.DummyWithClassFromMockPackage;

@SociableTest
class CanInstantiateDummyWithClassFromMockPackageTest {
    @TestSubject
    DummyWithClassFromMockPackage subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        assertThat(subject.getToMock()).isNotNull();
        assertThat(mockingDetails(subject.getToMock()).isMock()).isTrue();
    }
}