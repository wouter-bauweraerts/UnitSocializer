package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockingDetails;

import java.util.Random;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.Predefined;
import io.github.wouterbauweraerts.sociabletesting.annotation.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.predefined.PredefinedService;

@SociableTest
class DummyWithAnnotatedClassToMockTest {
    private static final Random RND = new Random();
    @TestSubject
    DummyWithAnnotatedClassToMock subject;

    @InjectTestInstance
    AnnotatedDummyToMock mockedDummy;

    @Predefined
    PredefinedService predefined = new PredefinedService(
            RND.nextInt(),
            "String with random int: %d".formatted(RND.nextInt())
    );

    @Test
    void createsExpected() {
        assertThat(subject).isNotNull();

        assertThat(subject.getDummy()).isNotNull();
        assertThat(mockingDetails(subject.getDummy()).isMock()).isTrue();

        assertThat(mockedDummy).isNotNull();
        assertThat(subject.getDummy()).isSameAs(mockedDummy);

        assertThat(predefined).isNotNull();
        assertThat(subject.getPredefinedService()).isSameAs(predefined);
    }
}