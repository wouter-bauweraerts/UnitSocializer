package io.github.wouterbauweraerts.sociabletesting.extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import io.github.wouterbauweraerts.sociabletesting.annotation.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.annotation.Predefined;
import io.github.wouterbauweraerts.sociabletesting.annotation.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.TestSubjectFactory;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.sociabletesting.core.state.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.util.ReflectionUtil;

public class BeforeEachCallbackHandler {
    private static final Logger LOGGER = Logger.getLogger(BeforeEachCallbackHandler.class.getName());
    private static final BeforeEachCallbackHandler INSTANCE = new BeforeEachCallbackHandler();

    private final SociableTestContext sociableTestContext;

    public static BeforeEachCallbackHandler getInstance() {
        return INSTANCE;
    }

    private static List<Field> filterFields(Field[] fields, Class<? extends Annotation> annotation) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(annotation))
                .toList();
    }

    private BeforeEachCallbackHandler() {
        sociableTestContext = SociableTestContext.getInstance();
    }

    public void beforeEach(Class<?> testClass, Object testInstance) {
        List<Field> predefinedFields = filterFields(testClass.getDeclaredFields(), Predefined.class);
        List<Field> testSubjects = filterFields(testClass.getDeclaredFields(), TestSubject.class);
        List<Field> fieldsToInject = filterFields(testClass.getDeclaredFields(), InjectTestInstance.class);

        if (testSubjects.isEmpty()) {
            throw new SociableTestException("No fields annotated with @TestSubject found!");
        }

        addPredefinedFields(testInstance, predefinedFields);
        instantiateObjects(testInstance, testSubjects);
        injectInstances(testInstance, fieldsToInject);
    }

    private void addPredefinedFields(Object testInstance, List<Field> predefinedFields) {
        LOGGER.fine("Handling @Predefined fields");

        predefinedFields.forEach(field -> {
            try {
                Class<?> predefinedFieldType = field.getType();
                Object predefinedFieldValue = ReflectionUtil.getFieldValue(field, testInstance);

                sociableTestContext.putIfAbsent(predefinedFieldType, predefinedFieldValue);
            } catch (Exception e) {
                throw new SociableTestException("Exception occurred while handling @Predefined fields", e);
            }
        });

    }

    private void instantiateObjects(Object testInstance, List<Field> testSubjects) {
        LOGGER.fine("Instantiating all @TestSubject annotated fields with their dependencies");

        testSubjects.forEach(field -> {
            try {
                ReflectionUtil.setFieldValue(field, testInstance, TestSubjectFactory.instantiate(field.getType()));
            } catch (Exception e) {
                throw new SociableTestInstantiationException("Unable to create test subject %s".formatted(field.getType().getName()), e);
            }
        });
    }

    private void injectInstances(Object testInstance, List<Field> fieldsToInject) {
        LOGGER.fine("Injecting created test instances");

        fieldsToInject.forEach(field -> {
            try {
                ReflectionUtil.setFieldValue(field, testInstance, sociableTestContext.get(field.getType()));
            } catch (Exception e) {
                throw new SociableTestException("Unable to inject test instance %s".formatted(field.getType().getName()), e);
            }
        });
    }
}
