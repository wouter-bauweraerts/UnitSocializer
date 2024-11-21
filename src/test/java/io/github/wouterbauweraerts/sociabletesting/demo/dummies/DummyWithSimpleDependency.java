package io.github.wouterbauweraerts.sociabletesting.demo.dummies;

public class DummyWithSimpleDependency {
    private final SimpleDummy simpleDummy;

    public DummyWithSimpleDependency(SimpleDummy simpleDummy) {
        this.simpleDummy = simpleDummy;
    }

    public SimpleDummy getSimpleDummy() {
        return simpleDummy;
    }
}
