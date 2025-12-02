package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.ConfigureMocking;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.config.MockConfigStrategy;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.unitsocializer.core.util.MockUtil;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy.InlineConfigurationDummyClassToMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class CanInstantiateDummyCLassWithPerTestMockConfigurationTest {
    @TestSubject
    DummyTestSubject subject;

    @Test
    @ConfigureMocking(annotations = DummyAnnotation.class)
    void testMockConfigurationAnnotationLevel() {
        assertThat(subject).isNotNull();
        assertThat(subject.inline).isNotNull();
        assertThat(subject.annotated).isNotNull();
        assertThat(MockUtil.isSpy(subject.inline))
                .withFailMessage("Expected subject.inline not to be a mock").isTrue();
        assertThat(MockUtil.isMock(subject.annotated))
                .withFailMessage("Expected subject.annotated to be a mock").isTrue();
    }

    @Test
    @ConfigureMocking(classes = InlineConfigurationDummyClassToMock.class)
    void testMockConfigurationClassLevel() {
        assertThat(subject).isNotNull();
        assertThat(subject.inline).isNotNull();
        assertThat(subject.annotated).isNotNull();
        assertThat(MockUtil.isMock(subject.inline))
                .withFailMessage("Expected subject.inline to be a mock").isTrue();
        assertThat(MockUtil.isSpy(subject.annotated))
                .withFailMessage("Expected subject.annotated not to be a mock").isTrue();
    }

    @Test
    @ConfigureMocking(packages = "io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy")
    void testMockConfigurationPackageLevel() {
        assertThat(subject).isNotNull();
        assertThat(subject.inline).isNotNull();
        assertThat(subject.annotated).isNotNull();
        assertThat(MockUtil.isMock(subject.inline))
                .withFailMessage("Expected subject.inline to be a mock").isTrue();
        assertThat(MockUtil.isSpy(subject.annotated))
                .withFailMessage("Expected subject.annotated not to be a mock").isTrue();
    }

    @Test
    @ConfigureMocking(
            packages = "io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy",
            annotations = DummyAnnotation.class
    )
    void testMockConfigurationPackageLevelMultipleConfigLevels() {
        assertThat(subject).isNotNull();
        assertThat(subject.inline).isNotNull();
        assertThat(subject.annotated).isNotNull();
        assertThat(MockUtil.isMock(subject.inline))
                .withFailMessage("Expected subject.inline to be a mock").isTrue();
        assertThat(MockUtil.isMock(subject.annotated))
                .withFailMessage("Expected subject.annotated to be a mock").isTrue();
    }

    @Test
    @ConfigureMocking(
            packages = "io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks.inline.dummy",
            strategy = MockConfigStrategy.MERGE
    )
    void testMockConfigurationPackageLevelMergeWithConfigFromFile() {
        assertThat(subject).isNotNull();
        assertThat(subject.inline).isNotNull();
        assertThat(subject.annotated).isNotNull();
        assertThat(MockUtil.isMock(subject.inline))
                .withFailMessage("Expected subject.inline to be a mock").isTrue();
        assertThat(MockUtil.isMock(subject.annotated))
                .withFailMessage("Expected subject.annotated to be a mock").isTrue();
    }

    public record DummyTestSubject(
            DummyWithAnnotation annotated,
            InlineConfigurationDummyClassToMock inline
    ) {
    }

    @DummyAnnotation
    public record DummyWithAnnotation() {}
}
