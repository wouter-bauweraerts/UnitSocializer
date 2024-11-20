package io.github.wouterbauweraerts.sociabletesting.core.config;

import java.lang.annotation.Annotation;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@JsonRootName("sociable-test")
public record MockingConfig(
        @JsonSetter(nulls= Nulls.SKIP, contentNulls = Nulls.FAIL)
        List<Class<? extends Annotation>> annotations,
        @JsonSetter(nulls= Nulls.SKIP, contentNulls = Nulls.FAIL)
        List<Class<?>> classes,
        @JsonSetter(nulls= Nulls.SKIP, contentNulls = Nulls.FAIL)
        List<String> packages
) {
}
