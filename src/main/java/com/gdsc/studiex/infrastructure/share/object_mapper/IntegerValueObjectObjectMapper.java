package com.gdsc.studiex.infrastructure.share.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gdsc.studiex.domain.share.models.IntegerValueObject;

import java.io.IOException;

public class IntegerValueObjectObjectMapper {
    public static class Serializer extends StdSerializer<IntegerValueObject> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<IntegerValueObject> t) {
            super(t);
        }

        @Override
        public void serialize(IntegerValueObject value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(value.asInt());
        }
    }

    public static class Deserializer extends StdDeserializer<IntegerValueObject> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public IntegerValueObject deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                              DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            return new IntegerValueObject(jsonParser.getIntValue(), jsonParser.getCurrentName());
        }
    }
}
