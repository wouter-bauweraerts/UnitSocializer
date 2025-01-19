package io.github.wouterbauweraerts.sociabletesting.core.extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import io.github.wouterbauweraerts.sociabletesting.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.Predefined;
import io.github.wouterbauweraerts.sociabletesting.core.annotations.TestSubject;
import io.github.wouterbauweraerts.sociabletesting.core.context.SociableTestContext;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;
import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.sociabletesting.core.helpers.InstanceHelper;
import io.github.wouterbauweraerts.sociabletesting.core.util.ReflectionUtil;

public class BeforeEachCallbackHandler {
    private static final Logger LOGGER = Logger.getLogger(BeforeEachCallbackHandler.class.getName());

    private final SociableTestContext sociableTestContext;
    private final InstanceHelper instanceHelper;

    private static List<Field> filterFields(Field[] fields, Class<? extends Annotation> annotation) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(annotation))
                .toList();
    }

    public BeforeEachCallbackHandler(SociableTestContext context, InstanceHelper instanceHelper) {
        this.sociableTestContext = context;
        this.instanceHelper = instanceHelper;
    }

    public void beforeEach(Class<?> testClass, Object testInstance) {
        List<Field> predefinedFields = filterFields(testClass.getDeclaredFields(), Predefined.class);
        List<Field> testSubjects = filterFields(testClass.getDeclaredFields(), TestSubject.class);
        List<Field> fieldsToInject = filterFields(testClass.getDeclaredFields(), InjectTestInstance.class);

        if (testSubjects.isEmpty()) {
            throw new SociableTestException("No fields annotated with @TestSubject found!");
        }

        addResolvers(testSubjects);
        addPredefinedFields(testInstance, predefinedFields);
        instantiateObjects(testInstance, testSubjects);
        injectInstances(testInstance, fieldsToInject);
    }

    private void addResolvers(List<Field> testSubjects) {
        testSubjects.stream()
                .map(subject -> subject.getDeclaredAnnotation(TestSubject.class))
                .flatMap(annotation -> Arrays.stream(annotation.typeResolvers()))
                .forEach(
                        typeConfig -> instanceHelper.getTypeResolver().addResolver(
                                typeConfig.forClass(),
                                typeConfig.use()
                        )
                );
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
                ReflectionUtil.setFieldValue(field, testInstance, instanceHelper.instantiate(field.getType()));
            } catch (SociableTestException ste) {
                throw ste;
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
