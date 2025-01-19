package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.resolve.specialization;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;

public record DummyWithConcreteDependency(
        DummyInterfaceMultipleImplementations dependency
) {
}
