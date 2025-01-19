package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.multi;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementations;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.SimpleDummy;
import io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.specialization.ConcreteType;

public record DummyWithMultipleTypes(
        ConcreteType concreteType,
        DummyAbstractClassMultipleImplementations abstractClass,
        DummyInterfaceMultipleImplementations interfaceType,
        SimpleDummy simpleType,
        int anInt
) {
}
