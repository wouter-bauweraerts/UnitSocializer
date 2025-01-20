package io.github.wouterbauweraerts.unitsocializer.core.exception;

/**
 * Exception indicating that an instantiation of a sociable test failed.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class SociableTestInstantiationException extends SociableTestException {
    
    /**
     * Constructs a new {@code SociableTestInstantiationException} with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception.
     */
    public SociableTestInstantiationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code SociableTestInstantiationException} with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of the exception.
     */
    public SociableTestInstantiationException(String msg, Exception cause) {
        super(msg, cause);
    }
}
