package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.ConfigureMocking;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.config.MockConfigStrategy;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.unitsocializer.core.util.MockUtil;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
@ConfigureMocking(
        annotations = CanInstantiateDummyWithDependencyAnnotatedToMockInlineConfigMergedConfigTest.InlineDummyAnnotation.class,
        strategy = MockConfigStrategy.MERGE
)
class CanInstantiateDummyWithDependencyAnnotatedToMockInlineConfigMergedConfigTest {
    @TestSubject
    DummyWithAnnotatedClassToMock subject;

    @InjectTestInstance
    AnnotatedDummyToMock mockedDummy;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();

        assertThat(subject.dummy()).isNotNull();
        assertThat(MockUtil.isMock(subject.dummy())).isTrue();

        assertThat(subject.dummyFromFile()).isNotNull();
        assertThat(MockUtil.isMock(subject.dummyFromFile())).isTrue();

        assertThat(mockedDummy).isNotNull();
        assertThat(subject.dummy()).isSameAs(mockedDummy);
    }

    public record DummyWithAnnotatedClassToMock(
            AnnotatedDummyToMock dummy,
            DummyAnnotatedFromFile dummyFromFile
    ) {
    }

    @InlineDummyAnnotation
    public static class AnnotatedDummyToMock {
    }

    @Retention(value = RUNTIME)
    public @interface InlineDummyAnnotation {}

    @DummyAnnotation
    public record DummyAnnotatedFromFile() {}
}