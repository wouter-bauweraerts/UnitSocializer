package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.interfaces;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceMultipleImplementations;

public class DependsOnDummyWithMultipleImplementations {
    private DummyInterfaceMultipleImplementations dependency;

    public DependsOnDummyWithMultipleImplementations(DummyInterfaceMultipleImplementations dependency) {
        this.dependency = dependency;
    }
}
