package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.mocks;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.DummyClassToMock;
import io.github.wouterbauweraerts.unitsocializer.core.util.MockUtil;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
class CanInstantiateDummyClassWithMockedDependencyTest {
    @TestSubject
    DummyWithClassToMock subject;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();
        Assertions.assertThat(subject.toMock()).isNotNull();
        assertThat(MockUtil.isMock(subject.toMock())).isTrue();
    }

    public record DummyWithClassToMock(DummyClassToMock toMock) {
    }
}