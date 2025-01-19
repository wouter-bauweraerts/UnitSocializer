package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Resolve;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementationsImpl1;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanResolveInterfaceImplementationWithAnnotationTest {
    @TestSubject(typeResolvers = @Resolve(
            forClass = DummyInterfaceMultipleImplementations.class,
            use = DummyInterfaceMultipleImplementationsImpl1.class
    ))
    DummyWithResolvedInterface dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        assertThat(dummy.dependency()).isNotNull()
                .isInstanceOf(DummyInterfaceMultipleImplementationsImpl1.class);
    }
}
