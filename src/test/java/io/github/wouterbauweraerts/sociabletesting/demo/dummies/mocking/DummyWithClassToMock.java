package io.github.wouterbauweraerts.sociabletesting.demo.dummies.mocking;

public class DummyWithClassToMock {
    private final DummyClassToMock toMock;

    public DummyWithClassToMock(DummyClassToMock toMock) {
        this.toMock = toMock;
    }

    public DummyClassToMock getToMock() {
        return toMock;
    }
}
