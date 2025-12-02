package io.github.wouterbauweraerts.unitsocializer.core.config;

import io.github.wouterbauweraerts.unitsocializer.core.annotations.ConfigureMocking;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.InjectTestInstance;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.Predefined;
import io.github.wouterbauweraerts.unitsocializer.core.annotations.Resolve;
import io.github.wouterbauweraerts.unitsocializer.core.context.SociableTestContext;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class MockingConfigTest {

    @TestFactory
    Stream<DynamicTest> testMerge() {
        return Stream.of(
                // Merge annotations without duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withAnnotations(Predefined.class, Resolve.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, InjectTestInstance.class, Predefined.class, Resolve.class)
                                .build()
                ),
                // Merge annotations with duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, InjectTestInstance.class)
                                .build()
                ),
                // Merge classes without duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withClasses(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withClasses(Predefined.class, Resolve.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withClasses(ConfigureMocking.class, InjectTestInstance.class, Predefined.class, Resolve.class)
                                .build()
                ),
                // Merge classes with duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withClasses(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withClasses(ConfigureMocking.class, InjectTestInstance.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withClasses(ConfigureMocking.class, InjectTestInstance.class)
                                .build()
                ),
                // Merge packages without duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withPackages("first", "second")
                                .build(),
                        MockConfigBuilder.builder()
                                .withPackages("third", "fourth")
                                .build(),
                        MockConfigBuilder.builder()
                                .withPackages("first", "second", "third", "fourth")
                                .build()
                ),
                // Merge packages with duplicates
                Triple.of(
                        MockConfigBuilder.builder()
                                .withPackages("first", "second")
                                .build(),
                        MockConfigBuilder.builder()
                                .withPackages("second", "third")
                                .build(),
                        MockConfigBuilder.builder()
                                .withPackages("first", "second", "third")
                                .build()
                ),
                // test combination 1-no-annotations no-packages classes, 2-annotations no-packages no-classes
                Triple.of(
                        MockConfigBuilder.builder()
                                .withClasses(MockConfigStrategy.class, SociableTestContext.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withAnnotations(ConfigureMocking.class, Resolve.class)
                                .build(),
                        MockConfigBuilder.builder()
                                .withClasses(MockConfigStrategy.class, SociableTestContext.class)
                                .withAnnotations(ConfigureMocking.class, Resolve.class)
                                .build()
                )
        ).map(triple -> DynamicTest.dynamicTest("merges to expected MockingConfig without any duplicates", () -> {
            MockingConfig result = MockingConfig.merge(triple.getLeft(), triple.getMiddle());
            assertThat(result).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(triple.getRight());
        }));
    }


    private static class MockConfigBuilder {
        private List<String> packages = new ArrayList<>();
        private List<Class<? extends Annotation>> annotations = new ArrayList<>();
        private List<Class<?>> classes = new ArrayList<>();

        public static MockConfigBuilder builder() {
            return new MockConfigBuilder();
        }

        public MockConfigBuilder withPackages (String... packages) {
            this.packages = Arrays.asList(packages);
            return this;
        }
        public MockConfigBuilder withAnnotations (Class<? extends Annotation>... annotations) {
            this.annotations = Arrays.asList(annotations);
            return this;
        }
        public MockConfigBuilder withClasses (Class<?>... classes) {
            this.classes = Arrays.asList(classes);
            return this;
        }

        public MockingConfig build() {
            return new MockingConfig(annotations, classes, packages);
        }
    }
}