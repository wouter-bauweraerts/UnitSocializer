package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

@SociableTest
class CanInstantiateDummyWithDependencyAnnotatedToMockDependencyTest {
    @TestSubject
    DummyWithAnnotatedClassToMock subject;

    @InjectTestInstance
    AnnotatedDummyToMock mockedDummy;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();

        assertThat(subject.dummy()).isNotNull();
        assertThat(mockingDetails(subject.dummy()).isMock()).isTrue();

        assertThat(mockedDummy).isNotNull();
        assertThat(subject.dummy()).isSameAs(mockedDummy);
    }

    public record DummyWithAnnotatedClassToMock(AnnotatedDummyToMock dummy) {
    }

    @DummyAnnotation
    public static class AnnotatedDummyToMock {
    }
}