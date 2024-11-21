package io.github.wouterbauweraerts.sociabletesting.demo.dummies.predefined;

public class PredefinedService {
    private final int intValue;
    private final String stringValue;

    public PredefinedService(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
