package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import io.github.wouterbauweraerts.sociabletesting.mocking.mockpackage.AmazingClass;

public class DummyWithClassFromMockPackage {
    private final AmazingClass toMock;

    public DummyWithClassFromMockPackage(AmazingClass toMock) {
        this.toMock = toMock;
    }

    public AmazingClass getToMock() {
        return toMock;
    }
}
