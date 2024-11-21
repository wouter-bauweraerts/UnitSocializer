package io.github.wouterbauweraerts.sociabletesting.extension.dummies.mocks;

import io.github.wouterbauweraerts.sociabletesting.predefined.PredefinedService;

public class DummyWithAnnotatedClassToMock {
    private final AnnotatedDummyToMock dummy;
    private final PredefinedService predefinedService;

    public DummyWithAnnotatedClassToMock(AnnotatedDummyToMock dummy, PredefinedService predefinedService) {
        this.dummy = dummy;
        this.predefinedService = predefinedService;
    }

    public AnnotatedDummyToMock getDummy() {
        return dummy;
    }

    public PredefinedService getPredefinedService() {
        return predefinedService;
    }
}
