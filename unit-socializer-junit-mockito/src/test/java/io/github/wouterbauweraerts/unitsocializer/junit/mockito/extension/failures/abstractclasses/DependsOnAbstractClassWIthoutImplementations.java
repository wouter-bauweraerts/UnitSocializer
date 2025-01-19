package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.failures.abstractclasses;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyAbstractClassNoImplementations;

@SuppressWarnings("unused")
public class DependsOnAbstractClassWIthoutImplementations {
    private DummyAbstractClassNoImplementations dependency;

    public DependsOnAbstractClassWIthoutImplementations(DummyAbstractClassNoImplementations dependency) {
        this.dependency = dependency;
    }
}
