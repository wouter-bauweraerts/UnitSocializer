package io.github.wouterbauweraerts.sociabletesting.extension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;

public class SociableTestExtension implements BeforeEachCallback {
    private static final Logger LOGGER = Logger.getLogger(SociableTestExtension.class.getName());
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(() -> new UnsupportedOperationException("TestClass not found!"));

        List<Field> testSubjects = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestSubject.class))
                .toList();

        if (testSubjects.isEmpty()) {
            throw new UnsupportedOperationException("No fields annotated with @TestSubject found!");
        }

        LOGGER.info("Instantiating all @TestSubject annotated fields with their dependencies");
    }
}
