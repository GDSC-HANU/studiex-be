package com.gdsc.studyex.infrastructure.share.objectMapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.exceptions.RuntimeBusinessLogicException;
import com.gdsc.studyex.domain.share.models.Id;

import java.io.IOException;

public class IdObjectMapper {
    public static class Serializer extends StdSerializer<Id> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<Id> t) {
            super(t);
        }

        @Override
        public void serialize(Id value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(value.toString());
        }
    }

    public static class Deserializer extends StdDeserializer<Id> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Id deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            try {
                return Id.parse(jsonParser.getValueAsString());
            } catch (InvalidInputException e) {
                throw new RuntimeBusinessLogicException(e.getMessage(), e.getCode());
            }
        }
    }
}
