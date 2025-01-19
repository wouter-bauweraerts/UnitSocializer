package io.github.wouterbauweraerts.unitsocializer.junit.mockito.extension.success.interfaces;

import io.github.wouterbauweraerts.unitsocializer.core.dummies.DummyInterface;

public class SimpleDummyWithInterfaceDependency {
    private DummyInterface interfaceDependency;

    public SimpleDummyWithInterfaceDependency(DummyInterface interfaceDependency) {
        this.interfaceDependency = interfaceDependency;
    }

    public DummyInterface getInterfaceDependency() {
        return interfaceDependency;
    }
}
