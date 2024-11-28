package io.github.wouterbauweraerts.sociabletesting.core.exception;

public class SociableTestException extends RuntimeException {
    public SociableTestException(String message) {
        super(message);
    }

    public SociableTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
