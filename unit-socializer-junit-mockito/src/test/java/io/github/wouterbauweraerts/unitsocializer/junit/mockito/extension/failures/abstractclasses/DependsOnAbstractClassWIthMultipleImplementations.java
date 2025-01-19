package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.failures.abstractclasses;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassMultipleImplementations;

@SuppressWarnings("unused")
public class DependsOnAbstractClassWIthMultipleImplementations {
    private DummyAbstractClassMultipleImplementations dependency;

    public DependsOnAbstractClassWIthMultipleImplementations(DummyAbstractClassMultipleImplementations dependency) {
        this.dependency = dependency;
    }
}
