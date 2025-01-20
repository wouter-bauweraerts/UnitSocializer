package io.github.wouterbauweraerts.unitsocializer.core.exception;


/**
 * Exception thrown when there are issues determining the type to instantiate
 * in sociable testing scenarios.
 *
 * @see java.lang.RuntimeException
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class SociableTestException extends RuntimeException {
    /**
     * Constructs a SociableTestException with the specified detail message.
     *
     * @param message the detail message
     */
    public SociableTestException(String message) {
        super(message);
    }

    /**
     * Constructs a SociableTestException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public SociableTestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for non-abstract types that cannot be instantiated.
     *
     * @param type the name of the type
     * @return a new SociableTestException with a detailed message
     */
    public static SociableTestException notAbstract(String type) {
        return new SociableTestException("Unable to determine type to instantiate for type %s. Not abstract".formatted(type));
    }

    /**
     * Creates an exception for abstract types with no implementations.
     *
     * @param type the name of the abstract type
     * @return a new SociableTestException with a detailed message
     */
    public static SociableTestException noImplementations(String type) {
        return new SociableTestException("Unable to determine type to instantiate for abstract type %s. No implementations".formatted(type));
    }

    /**
     * Creates an exception for abstract types with multiple implementations.
     *
     * @param type the name of the abstract type
     * @return a new SociableTestException with a detailed message
     */
    public static SociableTestException multipleImplementations(String type) {
        return new SociableTestException("Unable to determine type to instantiate for abstract type %s. Multiple implementations".formatted(type));
    }
}
