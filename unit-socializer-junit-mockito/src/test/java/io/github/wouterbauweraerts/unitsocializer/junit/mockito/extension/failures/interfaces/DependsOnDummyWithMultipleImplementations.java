package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.failures.interfaces;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterfaceMultipleImplementations;

@SuppressWarnings("unused")
public class DependsOnDummyWithMultipleImplementations {
    private DummyInterfaceMultipleImplementations dependency;

    public DependsOnDummyWithMultipleImplementations(DummyInterfaceMultipleImplementations dependency) {
        this.dependency = dependency;
    }
}
