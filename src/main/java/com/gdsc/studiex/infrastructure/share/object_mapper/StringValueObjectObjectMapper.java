package com.gdsc.studiex.infrastructure.share.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gdsc.studiex.domain.share.models.StringValueObject;

import java.io.IOException;

public class StringValueObjectObjectMapper {
    public static class Serializer extends StdSerializer<StringValueObject> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<StringValueObject> t) {
            super(t);
        }

        @Override
        public void serialize(StringValueObject value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(value.toString());
        }
    }

    public static class Deserializer extends StdDeserializer<StringValueObject> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public StringValueObject deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                             DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            return new StringValueObject(jsonParser.getValueAsString(), jsonParser.getCurrentName());
        }
    }
}
