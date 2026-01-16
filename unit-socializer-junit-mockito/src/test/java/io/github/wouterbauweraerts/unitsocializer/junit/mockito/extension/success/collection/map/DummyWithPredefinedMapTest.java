package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.map;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Predefined;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummyImplOne;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.dummy.AbstractDummyImplTwo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class DummyWithPredefinedMapTest {

    @Nested
    class PredefinedBuiltInType {
        @TestSubject
        DummyWithMapOfJavaTypes subject;

        @Predefined
        Map<Integer, String> predefinedList = Map.of(1, "Foo", 2, "Bar", 3, "Bat");

        public record DummyWithMapOfJavaTypes(Map<Integer, String> map) {}

        @Test
        void canInstantiateDummyWithPredefinedMapOfJavaTypes() {
            assertThat(subject).isNotNull();
            assertThat(subject.map).isNotNull()
                    .isEqualTo(predefinedList);
        }
    }

    @Nested
    class PredefinedListWithCustomType {
        @TestSubject
        DummyWithMapOfCustomType subject;

        @Predefined
        Map<String, AbstractDummy> predefinedList = Map.of(
                "One", new AbstractDummyImplOne(),
                "Two", new AbstractDummyImplTwo()
        );

        public record DummyWithMapOfCustomType(Map<String, AbstractDummy> list) {}

        @Test
        void canInstantiateDummyWithPredefinedMapHavinCustomValueType() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .containsExactlyEntriesOf(predefinedList);
        }
    }
}
