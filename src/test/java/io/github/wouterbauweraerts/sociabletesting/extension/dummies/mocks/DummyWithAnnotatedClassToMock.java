package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

public class DummyWithAnnotatedClassToMock {
    private final AnnotatedDummyToMock dummy;

    public DummyWithAnnotatedClassToMock(AnnotatedDummyToMock dummy) {
        this.dummy = dummy;
    }

    public AnnotatedDummyToMock getDummy() {
        return dummy;
    }
}
