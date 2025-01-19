package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.Resolve;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementationsImpl2;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementationsImpl1;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;

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
