package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementations;

public record DummyWithResolvedAbstractClass(
        DummyAbstractClassMultipleImplementations dependency
) {
}
