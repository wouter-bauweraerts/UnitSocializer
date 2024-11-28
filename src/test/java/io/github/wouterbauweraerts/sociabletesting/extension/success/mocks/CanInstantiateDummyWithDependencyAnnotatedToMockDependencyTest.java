package io.github.wouterbauweraerts.sociabletesting.extension.success.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.core.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.core.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.mocking.DummyAnnotation;

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