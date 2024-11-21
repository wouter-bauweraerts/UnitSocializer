package io.github.wouterbauweraerts.sociabletesting.extension.success;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import java.util.Random;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.dummies.mocking.DummyAnnotation;

@SociableTest
class CanInstantiateDummyWithDependencyAnnotatedToMockDependencyTest {
    private static final Random RND = new Random();
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
    public class AnnotatedDummyToMock {
    }
}