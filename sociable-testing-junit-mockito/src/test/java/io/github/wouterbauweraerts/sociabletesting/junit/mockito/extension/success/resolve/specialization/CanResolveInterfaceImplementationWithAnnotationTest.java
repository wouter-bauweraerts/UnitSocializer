package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.specialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.Resolve;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementationsImpl1;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

@SociableTest
class CanResolveInterfaceImplementationWithAnnotationTest {
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
