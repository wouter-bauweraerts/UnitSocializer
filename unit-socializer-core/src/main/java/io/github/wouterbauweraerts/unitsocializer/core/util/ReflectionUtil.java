package io.github.wouterbauweraerts.unitsocializer.core.util;

import java.lang.reflect.Field;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;


/**
 * Utility class that provides methods for getting and setting field values using reflection.
 * This class is primarily designed for testing and other use cases where reflection is necessary.
 *
 * <p><strong>Note:</strong> This class handles field access and restores the original accessibility state, ensuring minimal disruption to the target object.</p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class ReflectionUtil {
    private ReflectionUtil() {
        // Implicitly hide default constructor
    }

    /**
     * Retrieves the value of a specified field from the provided instance object.
     *
     * @param field    the {@link Field} object representing the field to access
     * @param instance the target instance object containing the field
     * @return the value of the field
     * @throws SociableTestException if the field's value could not be retrieved
     */
    public static Object getFieldValue(Field field, Object instance) {
        boolean originalAccessibility = field.canAccess(instance);

        try {
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw new SociableTestException("Unable to extract value from field: %s".formatted(field.getName()), e);
        } finally {
            field.setAccessible(originalAccessibility);
        }
    }

    /**
     * Sets the value of a specified field on the provided instance object.
     *
     * @param field    the {@link Field} object representing the field to modify
     * @param instance the target instance object containing the field
     * @param value    the new value to assign to the field
     * @throws SociableTestException if the field's value could not be set
     */
    public static void setFieldValue(Field field, Object instance, Object value) {
        boolean originalAccessibility = field.canAccess(instance);

        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            throw new SociableTestException("Unable to set value of field: %s".formatted(field.getName()), e);
        } finally {
            field.setAccessible(originalAccessibility);
        }
    }
}
