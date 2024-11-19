package io.github.wouterbauweraerts.sociabletesting.extension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.TestSubjectFactory;

public class SociableTestExtension implements BeforeEachCallback {
    private static final Logger LOGGER = Logger.getLogger(SociableTestExtension.class.getName());
    @Override
    public void beforeEach(ExtensionContext context) {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(() -> new UnsupportedOperationException("TestClass not found!"));

        List<Field> testSubjects = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestSubject.class))
                .toList();

        if (testSubjects.isEmpty()) {
            throw new UnsupportedOperationException("No fields annotated with @TestSubject found!");
        }

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
}
