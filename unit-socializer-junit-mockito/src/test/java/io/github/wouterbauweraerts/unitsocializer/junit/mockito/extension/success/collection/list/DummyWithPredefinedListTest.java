package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.list;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Predefined;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.list.dummy.AbstractDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.list.dummy.AbstractDummyImplOne;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.list.dummy.AbstractDummyImplTwo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class DummyWithPredefinedListTest {

    @Nested
    class PredefinedBuiltInType {
        @TestSubject
        DummyWithListOfIntegers subject;

        @Predefined
        List<Integer> predefinedList = List.of(1, 2, 3);

        public record DummyWithListOfIntegers(List<Integer> list) {}

        @Test
        void canInstantiateDummyWithPredefinedListOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .containsExactlyElementsOf(predefinedList);
        }
    }

    @Nested
    class PredefinedListWithCustomType {
        @TestSubject
        DummyWithListOfCustomType subject;

        @Predefined
        List<AbstractDummy> predefinedList = List.of(
                new AbstractDummyImplOne(),
                new AbstractDummyImplTwo()
        );

        public record DummyWithListOfCustomType(List<AbstractDummy> list) {}

        @Test
        void canInstantiateDummyWithPredefinedList() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .containsExactlyElementsOf(predefinedList);
        }
    }
}
