package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.abstractclass;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.Resolve;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementationsImpl2;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.annotations.SociableTest;

@SociableTest
class CanResolveAbstractClassImplementationWithAnnotationTest {
    @TestSubject(typeResolvers = @Resolve(
            forClass = DummyAbstractClassMultipleImplementations.class,
            use = DummyAbstractClassMultipleImplementationsImpl2.class
    ))
    DummyWithResolvedAbstractClass dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull();
        assertThat(dummy.dependency()).isNotNull()
                .isInstanceOf(DummyAbstractClassMultipleImplementationsImpl2.class);
    }
}
