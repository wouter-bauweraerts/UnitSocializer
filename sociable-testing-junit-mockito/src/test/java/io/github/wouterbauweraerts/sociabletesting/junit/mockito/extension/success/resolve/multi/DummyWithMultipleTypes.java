package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.multi;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.sociabletesting.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.specialization.ConcreteType;

public record DummyWithMultipleTypes(
        ConcreteType concreteType,
        DummyAbstractClassMultipleImplementations abstractClass,
        DummyInterfaceMultipleImplementations interfaceType,
        SimpleDummy simpleType,
        int anInt
) {
}
