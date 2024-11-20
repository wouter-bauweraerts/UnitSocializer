package io.github.wouterbauweraerts.sociabletesting.core.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.sociabletesting.mocking.DummyClassToMock;

class MockingConfigReaderTest {
    @Test
    void readConfigReturnsExpected() {
        MockingConfig mockingConfig = MockingConfigReader.loadConfig();

        assertThat(mockingConfig.packages()).containsExactly("org.springframework.data.jpa.repository", "io.github.wouterbauweraerts.sociabletesting.mocking.mockpackage");
        assertThat(mockingConfig.annotations()).containsExactly(DummyAnnotation.class);
        assertThat(mockingConfig.classes()).containsExactly(DummyClassToMock.class);
    }
}