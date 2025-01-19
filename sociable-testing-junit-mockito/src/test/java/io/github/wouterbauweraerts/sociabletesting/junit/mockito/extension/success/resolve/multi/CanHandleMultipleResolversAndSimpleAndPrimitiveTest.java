package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.multi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.Resolve;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementationsImpl1;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementationsImpl2;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.annotations.SociableTest;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.specialization.ConcreteType;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.specialization.ConcreteTypeSpecialization;

@SociableTest
class CanHandleMultipleResolversAndSimpleAndPrimitiveTest {
    @TestSubject(
            typeResolvers = {
                    @Resolve(forClass =  ConcreteType.class, use = ConcreteTypeSpecialization.class),
                    @Resolve(forClass = DummyAbstractClassMultipleImplementations.class, use = DummyAbstractClassMultipleImplementationsImpl1.class),
                    @Resolve(forClass = DummyInterfaceMultipleImplementations.class, use = DummyInterfaceMultipleImplementationsImpl2.class)
            }
    )
    DummyWithMultipleTypes dummy;

    @Test
    void test() {
        assertThat(dummy).isNotNull()
                .hasNoNullFieldsOrProperties();

        assertThat(dummy.concreteType()).isInstanceOf(ConcreteTypeSpecialization.class);
        assertThat(dummy.abstractClass()).isInstanceOf(DummyAbstractClassMultipleImplementationsImpl1.class);
        assertThat(dummy.interfaceType()).isInstanceOf(DummyInterfaceMultipleImplementationsImpl2.class);

    }
}
