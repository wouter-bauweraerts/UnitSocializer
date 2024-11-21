package io.github.wouterbauweraerts.sociabletesting.core.config;

import java.lang.annotation.Annotation;
import java.util.List;

public record MockingConfig(
        List<Class<? extends Annotation>> annotations,
        List<Class<?>> classes,
        List<String> packages
) {
}
