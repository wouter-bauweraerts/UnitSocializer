package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.ConfigureMocking;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.config.MockConfigStrategy;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy.InlineConfigurationDummyClassToMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

@SociableTest
@ConfigureMocking(
        packages = "io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy",
        strategy = MockConfigStrategy.MERGE
)
public class CanInstantiateDummyClassWithInlinedMockConfigurationOnPackageMergedConfigTest {
    @TestSubject
    DummyClassWithInlinedToMock subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        assertThat(subject.toMock()).isNotNull();
        assertThat(subject.toMockFromFile()).isNotNull();
        assertThat(mockingDetails(subject.toMock()).isMock()).isTrue();
        assertThat(mockingDetails(subject.toMockFromFile()).isMock()).isTrue();
    }

    public record DummyClassWithInlinedToMock(
            InlineConfigurationDummyClassToMock toMock,
            DummyAnnotatedFromFile toMockFromFile
    ) {}

    @DummyAnnotation
    public record DummyAnnotatedFromFile() {}
}
