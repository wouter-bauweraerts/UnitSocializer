package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.collection.set;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SociableTest
public class SetupContextWithGeneratedSetTest {
    @Nested
    class SetOfJavaType {
        @TestSubject
        DummyWithJavaTypeSet subject;

        @Test
        void canInstantiateDummyWithPredefinedSetOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .isInstanceOf(HashSet.class)
                    .isNotEmpty()
                    .allSatisfy(el -> assertThat(el).isInstanceOf(Integer.class));
        }

        public record DummyWithJavaTypeSet(Set<Integer> set) {
        }
    }

    @Nested
    class SetOfJavaTypeString {
        @TestSubject
        DummyWithJavaTypeSet subject;

        @Test
        void canInstantiateDummyWithPredefinedSetOfIntegers() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .isInstanceOf(LinkedHashSet.class)
                    .isNotEmpty()
                    .allSatisfy(el -> assertThat(el).isInstanceOf(String.class));
        }

        public record DummyWithJavaTypeSet(LinkedHashSet<String> set) {
        }
    }

    @Nested
    class SetOfCustomType {
        @TestSubject
        DummyWithSetOfCustomType subject;

        @Test
        void canCreateInstanceWithSetOfCustomTypeWithAllImplementations() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .isInstanceOf(HashSet.class)
                    .hasSize(2)
                    .satisfies(lst -> {
                        assertThat(lst.stream().anyMatch(AbstractDummyImplOne.class::isInstance)).isTrue();
                        assertThat(lst.stream().anyMatch(AbstractDummyImplTwo.class::isInstance)).isTrue();
                    });
        }

        public record DummyWithSetOfCustomType(Set<AbstractDummyForGenerateSet> set) {}

        public interface AbstractDummyForGenerateSet {}
        public record AbstractDummyImplOne() implements AbstractDummyForGenerateSet {}
        public record AbstractDummyImplTwo(int integerValue) implements AbstractDummyForGenerateSet {}
    }

    @Nested
    class SetOfCustomGenericType {
        @TestSubject
        DummyWithSetOfCustomGenericType subject;

        @Test
        void canCreateInstanceWithSetOfCustomTypeWithAllImplementations() {
            assertThat(subject).isNotNull();
            assertThat(subject.set).isNotNull()
                    .isInstanceOf(HashSet.class)
                    .hasSize(2)
                    .satisfies(lst -> {
                        assertThat(lst.stream().anyMatch(AbstractDummyImplOne.class::isInstance)).isTrue();
                        assertThat(lst.stream().anyMatch(AbstractDummyImplTwo.class::isInstance)).isTrue();
                    });
        }

        public record DummyWithSetOfCustomGenericType(Set<AbstractDummyGenericType<?>> set) {}

        public interface AbstractDummyGenericType<T> {
            T value();
        }

        public record AbstractDummyImplOne(String value) implements AbstractDummyGenericType<String> {}

        public record AbstractDummyImplTwo(Integer value) implements AbstractDummyGenericType<Number> {}
    }
}
