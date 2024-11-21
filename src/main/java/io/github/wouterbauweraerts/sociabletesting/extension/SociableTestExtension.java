package io.github.wouterbauweraerts.sociabletesting.extension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.Predefined;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.TestSubjectFactory;
import io.github.wouterbauweraerts.sociabletesting.core.state.SociableTestContext;

public class SociableTestExtension implements BeforeEachCallback {
    private static final Logger LOGGER = Logger.getLogger(SociableTestExtension.class.getName());
    private static final SociableTestContext sociableTestContext = SociableTestContext.getInstance();

    @Override
    public void beforeEach(ExtensionContext context) {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(() -> new UnsupportedOperationException("TestClass not found!"));

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

        addPredefinedFields(context, predefinedFields);
        instantiateObjects(context, testSubjects);
        injectInstances(context, fieldsToInject);
    }

    private static void addPredefinedFields(ExtensionContext context, List<Field> predefinedFields) {
        LOGGER.info("Handling @Predefined fields");

        predefinedFields.forEach(field -> {
            boolean originalAccessibility = field.canAccess(context.getRequiredTestInstance());
            try {
                field.setAccessible(true);

                Class<?> predefinedFieldType = field.getType();
                Object predefinedFieldValue = field.get(context.getRequiredTestInstance());

                sociableTestContext.putIfAbsent(predefinedFieldType, predefinedFieldValue);
            } catch (Exception e) {
// TODO add exception handling
            } finally {
                field.setAccessible(originalAccessibility);
            }
        });

    }

    private static void instantiateObjects(ExtensionContext context, List<Field> testSubjects) {
        LOGGER.info("Instantiating all @TestSubject annotated fields with their dependencies");

        testSubjects.forEach(field -> {
            field.setAccessible(true);

            try {
                field.set(context.getRequiredTestInstance(), TestSubjectFactory.instantiate(field.getType()));
            } catch (Exception e) {
                throw new IllegalStateException("Unable to create test subject %s".formatted(field.getType().getName()), e);
            }

            field.setAccessible(false);
        });
    }

    private static void injectInstances(ExtensionContext context, List<Field> fieldsToInject) {
        LOGGER.info("Injecting created test instances");

        fieldsToInject.forEach(field -> {
            field.setAccessible(true);

            try {
                field.set(context.getRequiredTestInstance(), sociableTestContext.get(field.getType()));
            } catch (Exception e) {
                throw new IllegalStateException("Unable to inject test instance %s".formatted(field.getType().getName()), e);
            }

            field.setAccessible(false);
        });
    }
}
