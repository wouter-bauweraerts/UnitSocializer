package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.ConfigureMocking;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

@SociableTest
@ConfigureMocking(annotations = CanInstantiateDummyWithDependencyAnnotatedToMockInlineConfigTest.InlineDummyAnnotation.class)
class CanInstantiateDummyWithDependencyAnnotatedToMockInlineConfigTest {
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

    @InlineDummyAnnotation
    public static class AnnotatedDummyToMock {
    }

    @Retention(value = RUNTIME)
    public @interface InlineDummyAnnotation {}
}