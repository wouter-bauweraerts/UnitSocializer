package io.github.wouterbauweraerts.sociabletesting.core;

public class SociableTestInstantiationException extends RuntimeException {
    public SociableTestInstantiationException(String message) {
        super(message);
    }

    public SociableTestInstantiationException(String msg, Exception cause) {
        super(msg, cause);
    }
}
