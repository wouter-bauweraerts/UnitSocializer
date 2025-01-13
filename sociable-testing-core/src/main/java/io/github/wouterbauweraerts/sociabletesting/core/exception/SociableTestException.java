package io.github.wouterbauweraerts.sociabletesting.core.exception;

public class SociableTestException extends RuntimeException {
    public SociableTestException(String message) {
        super(message);
    }

    public SociableTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public static SociableTestException notAbstract(String type) {
        return new SociableTestException("Unable to determine type to instantiate for type %s. Not abstract".formatted(type));
    }

    public static SociableTestException noImplementations(String type) {
        return new SociableTestException("Unable to determine type to instantiate for abstract type %s. No implementations".formatted(type));
    }

    public static SociableTestException multipleImplementations(String type) {
        return new SociableTestException("Unable to determine type to instantiate for abstract type %s. Multiple implementations".formatted(type));
    }
}
