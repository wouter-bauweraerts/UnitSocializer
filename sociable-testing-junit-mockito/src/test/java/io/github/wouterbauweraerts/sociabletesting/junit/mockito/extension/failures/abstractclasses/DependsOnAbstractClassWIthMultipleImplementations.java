package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.failures.abstractclasses;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyAbstractClassMultipleImplementations;

public class DependsOnAbstractClassWIthMultipleImplementations {
    private DummyAbstractClassMultipleImplementations dependency;

    public DependsOnAbstractClassWIthMultipleImplementations(DummyAbstractClassMultipleImplementations dependency) {
        this.dependency = dependency;
    }
}
