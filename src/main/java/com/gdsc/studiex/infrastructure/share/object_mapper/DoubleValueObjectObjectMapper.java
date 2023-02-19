package com.gdsc.studiex.infrastructure.share.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gdsc.studiex.domain.share.models.DoubleValueObject;
import com.gdsc.studiex.domain.share.models.IntegerValueObject;

import java.io.IOException;

public class DoubleValueObjectObjectMapper {
    public static class Serializer extends StdSerializer<DoubleValueObject> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<DoubleValueObject> t) {
            super(t);
        }

        @Override
        public void serialize(DoubleValueObject value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(value.asDouble());
        }
    }

    public static class Deserializer extends StdDeserializer<DoubleValueObject> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public DoubleValueObject deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                             DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            return new DoubleValueObject(jsonParser.getDoubleValue(), jsonParser.getCurrentName());
        }
    }
}
