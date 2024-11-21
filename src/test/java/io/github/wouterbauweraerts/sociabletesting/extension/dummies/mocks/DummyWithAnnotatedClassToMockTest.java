package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;

@SociableTest
class DummyWithAnnotatedClassToMockTest {
    @TestSubject
    DummyWithAnnotatedClassToMock subject;

    @InjectTestInstance
    AnnotatedDummyToMock mockedDummy;

//    @PredefinedTestInstance
//    Huppeldepup huppeldepup;

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();

        assertThat(subject.getDummy()).isNotNull();
        assertThat(mockingDetails(subject.getDummy()).isMock()).isTrue();

        assertThat(mockedDummy).isNotNull();
        assertThat(subject.getDummy()).isSameAs(mockedDummy);
    }
}