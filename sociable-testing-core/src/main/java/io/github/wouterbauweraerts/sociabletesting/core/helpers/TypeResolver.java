package io.github.wouterbauweraerts.sociabletesting.core.helpers;

import static java.util.Objects.isNull;

import java.lang.reflect.Modifier;
import java.util.List;

import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;

import io.github.wouterbauweraerts.sociabletesting.core.exception.SociableTestException;

public class TypeResolver {
    public boolean isAbstract(Class<?> clazz) {
        int classModifiers = clazz.getModifiers();
        return clazz.isInterface() || Modifier.isInterface(classModifiers) || Modifier.isAbstract(classModifiers);
    }

    public <T> Class<? extends T> resolveType(Class<T> abstractType) {
        List<Class<?>> implementations = findImplementations(abstractType)
                .stream()
                .filter(c -> !this.isAbstract(c))
                .toList();
        if (isNull(implementations) || implementations.isEmpty()) {
            throw SociableTestException.noImplementations(abstractType.getSimpleName());
        }

        if (implementations.size() > 1) {
            throw SociableTestException.multipleImplementations(abstractType.getSimpleName());
        }

        return (Class<? extends T>) implementations.getFirst();
    }

    private <T> List<Class<?>> findImplementations(Class<T> abstractType) {
        if (!isAbstract(abstractType)) {
            throw SociableTestException.notAbstract(abstractType.getSimpleName());
        }
        return doFind(abstractType);
    }

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
