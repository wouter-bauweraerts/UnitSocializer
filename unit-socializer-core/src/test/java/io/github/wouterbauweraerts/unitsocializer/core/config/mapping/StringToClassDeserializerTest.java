package io.github.wouterbauweraerts.unitsocializer.core.config.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;

import io.github.wouterbauweraerts.unitsocializer.core.config.MockingConfigReader;
import io.github.wouterbauweraerts.unitsocializer.core.dummies.mocking.DummyAnnotation;
import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;

@ExtendWith(MockitoExtension.class)
class StringToClassDeserializerTest {
    StringToClassDeserializer serializer = new StringToClassDeserializer();
    Class<?> result;

    @Mock
    JsonParser jsonParser;
    @Mock
    DeserializationContext deserializationContext;

    @Test
    void deserialize_whenClassNotFoundOnClassPath_returnsNull() throws Exception {
        setupReadValue("com.example.non.existing.SomeClass");

        assertThatCode(() -> result = serializer.deserialize(jsonParser, deserializationContext))
                .doesNotThrowAnyException();

        assertThat(result).isNull();
    }

    @Test
    void deserialize_whenContextIsNotAnnotations_returnsExpectedClass() throws Exception {
        Class<MockingConfigReader> clazz = MockingConfigReader.class;

        setupReadValue(clazz.getCanonicalName());
        setupContextParentName("classes");

        assertThatCode(() -> result = serializer.deserialize(jsonParser, deserializationContext))
                .doesNotThrowAnyException();

        assertThat(result).isEqualTo(clazz);
    }

    @Test
    void deserialize_whenContextIsAnnotations_andResultIsNoAnnotation_throwsExpected() throws Exception {
        Class<MockingConfigReader> clazz = MockingConfigReader.class;

        setupReadValue(clazz.getCanonicalName());
        setupContextParentName("annotations");

        assertThatThrownBy(() -> serializer.deserialize(jsonParser, deserializationContext))
                .isInstanceOf(SociableTestInstantiationException.class)
                .hasMessage("%s is not an annotation".formatted(clazz.getCanonicalName()));

        assertThat(result).isNull();
    }

    @Test
    void deserialize_whenContextIsAnnotations_returnsExpected() throws Exception {
        Class<DummyAnnotation> clazz = DummyAnnotation.class;

        setupReadValue(clazz.getCanonicalName());
        setupContextParentName("annotations");

        assertThatCode(() -> result = serializer.deserialize(jsonParser, deserializationContext))
                .doesNotThrowAnyException();

        assertThat(result).isEqualTo(clazz);
    }

    private void setupReadValue(String expectation) throws Exception {
        when(deserializationContext.readValue(jsonParser, String.class)).thenReturn(expectation);
    }

    private void setupContextParentName(String expectation) {
        JsonStreamContext parsingContext = mock(JsonStreamContext.class);
        JsonStreamContext parent = mock(JsonStreamContext.class);

        when(jsonParser.getParsingContext()).thenReturn(parsingContext);
        when(parsingContext.getParent()).thenReturn(parent);
        when(parent.getCurrentName()).thenReturn(expectation);
    }
}