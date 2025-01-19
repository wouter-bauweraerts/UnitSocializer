package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.abstractclass;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementations;

public record DummyWithResolvedAbstractClass(
        DummyAbstractClassMultipleImplementations dependency
) {
}
