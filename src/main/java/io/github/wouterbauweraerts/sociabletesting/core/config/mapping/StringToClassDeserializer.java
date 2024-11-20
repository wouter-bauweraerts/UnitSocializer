package io.github.wouterbauweraerts.sociabletesting.core.config.mapping;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import io.github.wouterbauweraerts.sociabletesting.core.SociableTestInstantiationException;

public class StringToClassDeserializer extends StdDeserializer<Class<?>> {
    protected StringToClassDeserializer() {
        super(Class.class);
    }

    @Override
    public Class<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
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
