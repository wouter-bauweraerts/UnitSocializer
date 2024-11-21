package io.github.wouterbauweraerts.sociabletesting.demo.dummies.annotated;

import io.github.wouterbauweraerts.sociabletesting.demo.dummies.predefined.PredefinedService;

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
