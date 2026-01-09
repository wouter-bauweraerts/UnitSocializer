package io.github.wouterbauweraerts.unitsocializer.core.helpers.classpath;

import static org.junit.platform.commons.util.ReflectionUtils.isAbstract;

import java.io.File;
import java.util.List;

import io.github.wouterbauweraerts.unitsocializer.core.exception.RootPackageReachedWithoutResultsException;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestException;

public class ClasspathScanner {
    private static final Logger LOG = LoggerFactory.getLogger(ClasspathScanner.class);
    public static final ClasspathScanner INSTANCE = new ClasspathScanner();

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
        return doFind(abstractType, abstractType.getPackageName());
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
    private <T> List<Class<?>> doFind(Class<T> abstractType, String packageName) {
        LOG.warn("Searching classpath for concrete implementations of '{}'.", abstractType.getSimpleName());

        ComponentSupplier componentSupplier = ComponentSupplier.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();


        try(
                ClassCriteria cc = ClassCriteria.create()
                        .packageName(pn -> pn.startsWith(packageName))
                        .and()
                        .allThoseThatMatch(abstractType::isAssignableFrom)
        ) {
            List<Class<?>> results = classHunter.findBy(SearchConfig.forPaths(convertPackageToPath(packageName)).byCriteria(cc)).getClasses()
                    .stream()
                    .toList();

            LOG.warn("Search result: implementations of '{}': {}.", abstractType.getSimpleName(), results.toString());

            if (results.isEmpty() || packageName.isEmpty() || results.stream().allMatch(c -> c.equals(abstractType))) {
                return doFind(abstractType, getParentPackage(packageName));
            } else  {
                return results;
            }
        } catch (RootPackageReachedWithoutResultsException ste) {
            LOG.warn("Finished scanning classpath without finding any implementations of '{}'.", abstractType.getSimpleName());
            LOG.warn(ste.getMessage());
            return List.of();
        } catch (Exception e) {
            throw new SociableTestException("Something went wrong while resolving type of " + abstractType, e);
        }
    }

    private static String getParentPackage(String packageName) {
        int lastPackageSeparator = packageName.lastIndexOf('.');

        if (lastPackageSeparator == -1) {
            throw new RootPackageReachedWithoutResultsException("Package %s is the root package.".formatted(packageName));
        }
        return packageName.substring(0, packageName.lastIndexOf('.'));
    }

    private String convertPackageToPath(String packageName) {
        return packageName.replace('.', File.pathSeparatorChar);
    }
}
