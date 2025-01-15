package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.abstractclasses;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassNoImplementations;

public class DependsOnAbstractClassWIthoutImplementations {
    private DummyAbstractClassNoImplementations dependency;

    public DependsOnAbstractClassWIthoutImplementations(DummyAbstractClassNoImplementations dependency) {
        this.dependency = dependency;
    }
}
