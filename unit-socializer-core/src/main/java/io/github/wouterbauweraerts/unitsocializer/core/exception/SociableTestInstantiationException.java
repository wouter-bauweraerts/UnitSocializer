package io.github.wouterbauweraerts.unitsocializer.core.exception;

public class SociableTestInstantiationException extends SociableTestException {
    public SociableTestInstantiationException(String message) {
        super(message);
    }

    public SociableTestInstantiationException(String msg, Exception cause) {
        super(msg, cause);
    }
}
