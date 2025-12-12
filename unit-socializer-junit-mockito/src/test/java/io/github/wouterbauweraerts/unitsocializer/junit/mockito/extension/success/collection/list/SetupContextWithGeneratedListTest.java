package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.list;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class SetupContextWithGeneratedListTest {
    @Nested
    public class ListOfJavaType {
        @TestSubject
        DummyWithJavaTypeList subject;

        @Test
        void canInstantiateDummyWithPredefinedListOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .isInstanceOf(ArrayList.class)
                    .isNotEmpty()
                    .allSatisfy(el -> assertThat(el).isInstanceOf(Integer.class));
        }

        public record DummyWithJavaTypeList(List<Integer> list) {
        }
    }

    @Nested
    public class ListOfJavaTypeString {
        @TestSubject
        DummyWithJavaTypeList subject;

        @Test
        void canInstantiateDummyWithPredefinedListOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .isInstanceOf(LinkedList.class)
                    .isNotEmpty()
                    .allSatisfy(el -> assertThat(el).isInstanceOf(String.class));
        }

        public record DummyWithJavaTypeList(LinkedList<String> list) {
        }
    }


}
