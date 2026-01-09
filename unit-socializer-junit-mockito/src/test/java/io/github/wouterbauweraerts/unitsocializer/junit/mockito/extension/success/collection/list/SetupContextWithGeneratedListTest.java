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

    @Nested
    public class ListOfCustomType {
        @TestSubject
        DummyWithListOfCustomType subject;

        @Test
        void canCreateInstanceWithListOfCustomTypeWithAllImplementations() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .isInstanceOf(ArrayList.class)
                    .hasSize(2)
                    .satisfies(lst -> {
                        assertThat(lst.stream().anyMatch(e -> e instanceof AbstractDummyImplOne)).isTrue();
                        assertThat(lst.stream().anyMatch(e -> e instanceof AbstractDummyImplTwo)).isTrue();
                    });
        }

        public record DummyWithListOfCustomType(List<AbstractDummyForGenerateList> list) {}

        public interface AbstractDummyForGenerateList {}
        public record AbstractDummyImplOne() implements AbstractDummyForGenerateList {}
        public record AbstractDummyImplTwo(int integerValue) implements AbstractDummyForGenerateList {}
    }

    @Nested
    public class ListOfCustomGenericType {
        @TestSubject
        DummyWithListOfCustomGenericType subject;

        @Test
        void canCreateInstanceWithListOfCustomTypeWithAllImplementations() {
            /*
            TODO When running this test in IDE, the test is flaky. Try to find a solution for this.
                 It runs just fine on the command line (no flakiness up till now)
             */
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .isInstanceOf(ArrayList.class)
                    .hasSize(2)
                    .satisfies(lst -> {
                        assertThat(lst.stream().anyMatch(e -> e instanceof AbstractDummyImplOne)).isTrue();
                        assertThat(lst.stream().anyMatch(e -> e instanceof AbstractDummyImplTwo)).isTrue();
                    });
        }

        public record DummyWithListOfCustomGenericType(List<AbstractDummyGenericType<?>> list) {}

        public interface AbstractDummyGenericType<T> {
            T value();
        }

        public record AbstractDummyImplOne(String value) implements AbstractDummyGenericType<String> {}

        public record AbstractDummyImplTwo(Integer value) implements AbstractDummyGenericType<Number> {}
    }

    @Nested
    public class ListOfCustomGenericTypeFiltered {
        @TestSubject
        DummyWithListOfCustomGenericType subject;

        @Test
        void canCreateInstanceWithListOfCustomTypeWithAllImplementations() {
            assertThat(subject).isNotNull();
            assertThat(subject.list).isNotNull()
                    .isInstanceOf(ArrayList.class)
                    .hasSize(1)
                    .satisfies(lst -> {
                        assertThat(lst.stream().anyMatch(e -> e instanceof AbstractDummyImplOne)).isTrue();
                    });
        }

        public record DummyWithListOfCustomGenericType(List<AbstractDummyGenericType<String>> list) {}

        public interface AbstractDummyGenericType<T> {
            T value();
        }

        public record AbstractDummyImplOne(String value) implements AbstractDummyGenericType<String> {}

        public record AbstractDummyImplTwo(Integer value) implements AbstractDummyGenericType<Number> {}
    }
}
