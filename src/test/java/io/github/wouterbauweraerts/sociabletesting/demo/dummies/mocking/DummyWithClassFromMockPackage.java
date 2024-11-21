package io.github.wouterbauweraerts.sociabletesting.demo.dummies.mocking;

import io.github.wouterbauweraerts.sociabletesting.demo.dummies.mocking.mockpackage.AmazingClass;

public class DummyWithClassFromMockPackage {
    private final AmazingClass toMock;

    public DummyWithClassFromMockPackage(AmazingClass toMock) {
        this.toMock = toMock;
    }

    public AmazingClass getToMock() {
        return toMock;
    }
}
