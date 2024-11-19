package io.github.wouterbauweraerts.sociabletesting.core;

import java.util.List;

public record MockingConfig(
        List<String> annotationsToMock,
        List<String> classesToMock
) {
}
