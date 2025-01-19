package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.resolve.interfaces;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementations;

public record DummyWithResolvedInterface(
        DummyInterfaceMultipleImplementations dependency
) {
}
