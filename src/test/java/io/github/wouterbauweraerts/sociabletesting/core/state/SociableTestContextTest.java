package io.github.wouterbauweraerts.sociabletesting.core.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.wouterbauweraerts.sociabletesting.demo.dummies.SimpleDummy;

class SociableTestContextTest {
    SociableTestContext ctx = SociableTestContext.getInstance();

    @BeforeEach
    void setUp() {
        ctx.clear();
    }

    @Test
    void initialMapIsEmpty() throws Exception {
        assertThat(getInstancesFromContext()).isEmpty();
    }

    @Test
    void clear_clearInstancesMap() throws Exception {
        ctx.putIfAbsent(String.class, "Foo");

        assertThat(getInstancesFromContext()).isNotEmpty();

        ctx.clear();
        assertThat(getInstancesFromContext()).isEmpty();
    }

    @Test
    void putIfAbsent_noPreviousEntry_addsToMap() throws Exception {
        String instance = "Foo";
        Object returnVal = ctx.putIfAbsent(String.class, instance);

        assertThat(returnVal).isNull();
        assertThat(getInstancesFromContext()).hasSize(1)
                .containsEntry(String.class, instance);
    }

    @Test
    void putIfAbsent_existingPreviousEntry_addsToMap() throws Exception {
        String originalInstance = "BAR";

        ctx.putIfAbsent(String.class, originalInstance);
        Object returnVal = ctx.putIfAbsent(String.class, "Foo");

        assertThat(returnVal).isSameAs(originalInstance);
        assertThat(getInstancesFromContext()).hasSize(1)
                .containsEntry(String.class, originalInstance);
    }

    @Test
    void get_instanceNotPresent_throwsExpected() {
        assertThatThrownBy(() -> ctx.get(String.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No instance found of class java.lang.String");
    }

    @Test
    void get_instancePresent_returnsInstance() {
        SimpleDummy dummy = new SimpleDummy();

        ctx.putIfAbsent(SimpleDummy.class, dummy);

        assertThat(ctx.get(SimpleDummy.class)).isSameAs(dummy);
    }

    @Test
    void exists_whenNoInstanceOfType_returnsExpected() {
        assertThat(ctx.exists(SimpleDummy.class)).isFalse();
    }

    @Test
    void exists_whenInstanceOfTypePresent_returnsExpected() {
        ctx.putIfAbsent(SimpleDummy.class, new SimpleDummy());
        assertThat(ctx.exists(SimpleDummy.class)).isTrue();
    }

    public Map<Class<?>, Object> getInstancesFromContext() throws Exception {
        Class<? extends SociableTestContext> ctxClass = ctx.getClass();
        Field instancesField = ctxClass.getDeclaredField("instances");
        instancesField.setAccessible(true);
        return (Map<Class<?>, Object>) instancesField.get(ctx);
    }
}