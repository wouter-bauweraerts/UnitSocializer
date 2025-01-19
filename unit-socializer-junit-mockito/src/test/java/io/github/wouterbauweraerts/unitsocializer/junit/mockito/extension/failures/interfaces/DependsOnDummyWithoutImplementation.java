package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.failures.interfaces;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceNoImplementations;

@SuppressWarnings("unused")
public class DependsOnDummyWithoutImplementation {
    private DummyInterfaceNoImplementations dependency;

    public DependsOnDummyWithoutImplementation(DummyInterfaceNoImplementations dependency) {
        this.dependency = dependency;
    }
}
