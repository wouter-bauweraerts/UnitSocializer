package io.github.wouterbauweraerts.sociabletesting.extension.dummies.simpledependency;

import io.github.wouterbauweraerts.sociabletesting.extension.dummies.simple.SimpleDummy;

public class DummyWithSimpleDependency {
    private final SimpleDummy simpleDummy;

    public DummyWithSimpleDependency(SimpleDummy simpleDummy) {
        this.simpleDummy = simpleDummy;
    }

    public SimpleDummy getSimpleDummy() {
        return simpleDummy;
    }
}
