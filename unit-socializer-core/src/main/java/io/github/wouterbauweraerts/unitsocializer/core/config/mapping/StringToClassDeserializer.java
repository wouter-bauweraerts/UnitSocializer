package io.github.wouterbauweraerts.unitsocializer.core.config.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;


/**
 * A custom deserializer to map a JSON string into a {@link Class} object.
 * <p>
 * This deserializer is especially useful when working with YAML configurations
 * where fully qualified class names need to be loaded dynamically. It ensures that the 
 * deserialized class is valid and provides additional checks for annotation types, 
 * throwing an exception when required.
 * </p>
 *
 * @author Wouter Bauweraerts
 * @since 0.0.1
 */
public class StringToClassDeserializer extends StdDeserializer<Class<?>> {
    /**
     * Constructs a new {@code StringToClassDeserializer} instance for converting a 
     * string into a {@link Class} object.
     * <p>
     * This constructor initializes the deserializer with the {@link Class} type as the target.
     * </p>
     */
    protected StringToClassDeserializer() {
        super(Class.class);
    }

    /**
     * Deserializes a string into a {@link Class} object.
     * <p>
     * This method converts a fully qualified class name string in into the corresponding
     * Java {@link Class} object. If the JSON structure indicates an annotation type but the 
     * deserialized class is not an annotation, a {@link SociableTestInstantiationException} is thrown. 
     * If the class cannot be resolved, {@code null} is returned instead of throwing  
     * {@link ClassNotFoundException}.
     * </p>
     *
     * @param jsonParser             the JSON parser used for reading the input string
     * @param deserializationContext the context for the deserialization process
     * @return the {@link Class} object corresponding to the deserialized string, or {@code null} if unable to resolve
     * @throws IOException                      if an input/output error occurs during deserialization
     * @throws SociableTestInstantiationException if the deserialized class is expected to be an annotation but is not
     */
    @Override
    public Class<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String stringValue = deserializationContext.readValue(jsonParser, String.class);
        try {
            Class<?> result = Class.forName(stringValue);
            if("annotations".equals(jsonParser.getParsingContext().getParent().getCurrentName()) && !result.isAnnotation()) {
                throw new SociableTestInstantiationException("%s is not an annotation".formatted(stringValue));
            }
            return result;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
