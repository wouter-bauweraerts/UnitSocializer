package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.interfaces;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterfaceNoImplementations;

public class DependsOnDummyWithoutImplementation {
    private DummyInterfaceNoImplementations dependency;

    public DependsOnDummyWithoutImplementation(DummyInterfaceNoImplementations dependency) {
        this.dependency = dependency;
    }

    public DummyInterfaceNoImplementations getDependency() {
        return dependency;
    }
}
