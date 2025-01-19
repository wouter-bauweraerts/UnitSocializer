package io.github.wouterbauweraerts.unitsocializer.core.config.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.github.wouterbauweraerts.unitsocializer.core.exception.SociableTestInstantiationException;

public class StringToClassDeserializer extends StdDeserializer<Class<?>> {
    protected StringToClassDeserializer() {
        super(Class.class);
    }

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
