package io.github.wouterbauweraerts.sociabletesting.junit.mockito.extension.success.interfaces;

import io.github.wouterbauweraerts.sociabletesting.core.dummies.DummyInterface;

public class SimpleDummyWithInterfaceDependency {
    private DummyInterface interfaceDependency;

    public SimpleDummyWithInterfaceDependency(DummyInterface interfaceDependency) {
        this.interfaceDependency = interfaceDependency;
    }

    public DummyInterface getInterfaceDependency() {
        return interfaceDependency;
    }
}
