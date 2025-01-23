package io.github.wouterbauweraerts.unitsocializer.core.helpers.classpath;

import static org.junit.platform.commons.util.ReflectionUtils.isAbstract;

import java.util.List;

import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;

public class ClasspathScanner {

    /**
     * Searches for all concrete implementations of the given abstract type.
     *
     * @param <T> the type to search for implementations of
     * @param abstractType the abstract class or interface to search for
     * @return a list of concrete classes implementing the abstract type
     * @throws SociableTestException if the provided class is not abstract
     */
    public  <T> List<Class<?>> findImplementations(Class<T> abstractType) {
        if (!isAbstract(abstractType)) {
            throw SociableTestException.notAbstract(abstractType.getSimpleName());
        }
        return doFind(abstractType);
    }

    /**
     * Performs the actual search for implementations of the given abstract type
     * using reflection and the Burningwave library.
     *
     * @param <T> the type to search for implementations of
     * @param abstractType the abstract class or interface to search for
     * @return a list of concrete classes implementing the abstract type
     * @throws SociableTestException if an error occurs during the search
     */
    private <T> List<Class<?>> doFind(Class<T> abstractType) {
        ComponentSupplier componentSupplier = ComponentSupplier.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();

        try(ClassCriteria cc = ClassCriteria.create().allThoseThatMatch(abstractType::isAssignableFrom)) {
            return classHunter.findBy(SearchConfig.byCriteria(cc)).getClasses()
                    .stream()
                    .toList();
        } catch (Exception e) {
            throw new SociableTestException("Something went wrong while resolving type of " + abstractType, e);
        }
    }
}
