package io.github.wouterbauweraerts.sociabletesting.extension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.Predefined;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.TestSubjectFactory;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.state.SociableTestContext;

public class BeforeEachCallbackHandler {
    private static final Logger LOGGER = Logger.getLogger(BeforeEachCallbackHandler.class.getName());
    private static final BeforeEachCallbackHandler INSTANCE = new BeforeEachCallbackHandler();

    private final SociableTestContext sociableTestContext;

    public static BeforeEachCallbackHandler getInstance() {
        return INSTANCE;
    }

    private BeforeEachCallbackHandler() {
        sociableTestContext = SociableTestContext.getInstance();
    }

    public void beforeEach(Class<?> testClass, Object testInstance) {
        List<Field> predefinedFields = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Predefined.class))
                .toList();

        List<Field> testSubjects = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestSubject.class))
                .toList();

        List<Field> fieldsToInject = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectTestInstance.class))
                .toList();

        if (testSubjects.isEmpty()) {
            throw new UnsupportedOperationException("No fields annotated with @TestSubject found!");
        }

        addPredefinedFields(testInstance, predefinedFields);
        instantiateObjects(testInstance, testSubjects);
        injectInstances(testInstance, fieldsToInject);
    }

    private void addPredefinedFields(Object testInstance, List<Field> predefinedFields) {
        LOGGER.fine("Handling @Predefined fields");

        predefinedFields.forEach(field -> {
            boolean originalAccessibility = field.canAccess(testInstance);
            try {
                field.setAccessible(true);

                Class<?> predefinedFieldType = field.getType();
                Object predefinedFieldValue = field.get(testInstance);

                sociableTestContext.putIfAbsent(predefinedFieldType, predefinedFieldValue);
            } catch (Exception e) {
                throw new SociableTestException("Exception occurred while handling @Predefined fields", e);
            } finally {
                field.setAccessible(originalAccessibility);
            }
        });

    }

    private void instantiateObjects(Object testInstance, List<Field> testSubjects) {
        LOGGER.fine("Instantiating all @TestSubject annotated fields with their dependencies");

        testSubjects.forEach(field -> {
            field.setAccessible(true);

            try {
                field.set(testInstance, TestSubjectFactory.instantiate(field.getType()));
            } catch (Exception e) {
                throw new IllegalStateException("Unable to create test subject %s".formatted(field.getType().getName()), e);
            }

            field.setAccessible(false);
        });
    }

    private void injectInstances(Object testInstance, List<Field> fieldsToInject) {
        LOGGER.fine("Injecting created test instances");

        fieldsToInject.forEach(field -> {
            field.setAccessible(true);

            try {
                field.set(testInstance, sociableTestContext.get(field.getType()));
            } catch (Exception e) {
                throw new IllegalStateException("Unable to inject test instance %s".formatted(field.getType().getName()), e);
            }

            field.setAccessible(false);
        });
    }
}
