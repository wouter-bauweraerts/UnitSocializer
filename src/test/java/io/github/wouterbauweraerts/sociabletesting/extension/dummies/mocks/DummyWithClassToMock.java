package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import io.github.wouterbauweraerts.sociabletesting.mocking.DummyClassToMock;

public class DummyWithClassToMock {
    private final DummyClassToMock toMock;

    public DummyWithClassToMock(DummyClassToMock toMock) {
        this.toMock = toMock;
    }

    public DummyClassToMock getToMock() {
        return toMock;
    }
}
