package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.interfaces;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;

public record DummyWithResolvedInterface(
        DummyInterfaceMultipleImplementations dependency
) {
}
