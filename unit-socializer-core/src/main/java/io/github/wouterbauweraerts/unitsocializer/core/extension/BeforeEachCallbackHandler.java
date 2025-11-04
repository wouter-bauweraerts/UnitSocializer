package io.github.wouterbauweraerts.unitsocializer.core.extension;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.Predefined;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.TestSubject;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;
import io.github.wouterbauweraerts.unitsocializer.core.helpers.InstanceHelper;
import io.github.wouterbauweraerts.unitsocializer.core.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles operations to be executed before each test method in a sociable test class.
 * It processes fields annotated with specific annotations and manages object instantiation,
 * dependency injection, and configuration for test execution.
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class BeforeEachCallbackHandler {
    private static final Logger LOGGER = Logger.getLogger(BeforeEachCallbackHandler.class.getName());

    private final SociableTestContext sociableTestContext;
    private final InstanceHelper instanceHelper;

    /**
     * Filters the provided list of fields, returning only those annotated with the specified annotation.
     *
     * @param fields     the array of fields to filter
     * @param annotation the annotation class to filter by
     * @return a list of fields annotated with the specified annotation
     */
    private static List<Field> filterFields(Field[] fields, Class<? extends Annotation> annotation) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(annotation))
                .toList();
    }

    /**
     * Constructs a new {@code BeforeEachCallbackHandler}.
     *
     * @param context         the sociable test context used for managing test-related objects
     * @param instanceHelper  the helper instance for creating and managing object instances
     */
    public BeforeEachCallbackHandler(SociableTestContext context, InstanceHelper instanceHelper) {
        this.sociableTestContext = context;
        this.instanceHelper = instanceHelper;
    }

    /**
     * Executes before each test, preparing the test instance by processing annotated fields for
     * dependency injection, instantiation, and configuration.
     *
     * @param testClass    the test class containing the test methods and annotated fields
     * @param testInstance the test instance to be prepared
     * @throws SociableTestException if no @TestSubject fields are found or other validation issues occur
     */
    public void beforeEach(Class<?> testClass, Object testInstance) {
        Field[] classFields = testClass.getDeclaredFields();
        List<Field> predefinedFields = filterFields(classFields, Predefined.class);
        List<Field> testSubjects = filterFields(classFields, TestSubject.class);
        List<Field> fieldsToInject = filterFields(classFields, InjectTestInstance.class);

        if (testSubjects.isEmpty()) {
            throw new SociableTestException("No fields annotated with @TestSubject found!");
        }

        addResolvers(testSubjects);
        addPredefinedFields(testInstance, predefinedFields);
        instantiateObjects(testInstance, testSubjects);
        injectInstances(testInstance, fieldsToInject);
    }

    /**
     * Adds type resolvers based on the @TestSubject annotations on the provided fields.
     *
     * @param testSubjects the list of fields annotated with @TestSubject
     */
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

    /**
     * Registers predefined field values into the sociable test context, allowing these
     * values to be shared across the test execution.
     *
     * @param testInstance     the test instance containing the annotated fields
     * @param predefinedFields the list of fields annotated with @Predefined
     * @throws SociableTestException if an error occurs while handling predefined fields
     */
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

    /**
     * Instantiates fields annotated with @TestSubject in the test instance, injecting any required dependencies.
     *
     * @param testInstance the test instance containing the annotated fields
     * @param testSubjects the list of fields annotated with @TestSubject
     * @throws SociableTestInstantiationException if an instantiation error occurs
     */
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

    /**
     * Injects dependencies into fields annotated with @InjectTestInstance.
     *
     * @param testInstance   the test instance containing the annotated fields
     * @param fieldsToInject the list of fields annotated with @InjectTestInstance
     * @throws SociableTestException if an error occurs during dependency injection
     */
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
