package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.specialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Resolve;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanResolveConcreteSpecializedImplementationTest {
    @TestSubject(typeResolvers = @Resolve(
            forClass = ConcreteType.class,
            use = ConcreteTypeSpecialization.class
    ))
    DummyWithConcreteDependency dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        assertThat(dummy.dependency()).isNotNull()
                .isInstanceOf(ConcreteTypeSpecialization.class);
    }
}
