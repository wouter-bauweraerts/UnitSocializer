package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.set;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Predefined;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummyImplOne;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummyImplTwo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class DummyWithPredefinedSetTest {

    @Nested
    class PredefinedBuiltInType {
        @TestSubject
        DummyWithSetOfIntegers subject;

        @Predefined
        Set<Integer> predefinedSet = Set.of(1, 2, 3);

        public record DummyWithSetOfIntegers(Set<Integer> set) {}

        @Test
        void canInstantiateDummyWithPredefinedListOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .containsExactlyElementsOf(predefinedSet);
        }
    }

    @Nested
    class PredefinedSetWithCustomType {
        @TestSubject
        DummyWithSetOfCustomType subject;

        @Predefined
        Set<AbstractDummy> predefinedSet = Set.of(
                new AbstractDummyImplOne(),
                new AbstractDummyImplTwo()
        );

        public record DummyWithSetOfCustomType(Set<AbstractDummy> set) {}

        @Test
        void canInstantiateDummyWithPredefinedList() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .containsExactlyElementsOf(predefinedSet);
        }
    }
}
