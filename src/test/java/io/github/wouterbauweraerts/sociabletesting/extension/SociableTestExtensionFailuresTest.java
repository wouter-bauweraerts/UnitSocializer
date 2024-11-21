package io.github.wouterbauweraerts.sociabletesting.extension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import io.github.wouterbauweraerts.sociabletesting.extension.dummies.MissingTestSubjectTest;

class SociableTestExtensionFailuresTest {

    @Test
    void startupShouldFailWhenNoTestSubject() {
        TestExecutionSummary summary = runTestMethod(MissingTestSubjectTest.class, "shouldFail");

        assertThat(summary.getFailures()).hasSize(1)
                .allSatisfy(failure -> assertThat(failure.getException()).isInstanceOf(UnsupportedOperationException.class)
                        .hasMessage("No fields annotated with @TestSubject found!"));
    }

    private TestExecutionSummary runTestMethod(Class<?> testClass, String methodName) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = request().selectors(selectMethod(testClass, methodName)).build();
        LauncherFactory.create().execute(request, listener);

        return listener.getSummary();
    }
}
