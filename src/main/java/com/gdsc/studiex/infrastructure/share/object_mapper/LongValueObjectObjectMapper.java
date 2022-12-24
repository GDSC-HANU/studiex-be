package com.gdsc.studiex.infrastructure.share.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gdsc.studiex.domain.share.models.LongValueObject;

import java.io.IOException;

public class LongValueObjectObjectMapper {
    public static class Serializer extends StdSerializer<LongValueObject> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<LongValueObject> t) {
            super(t);
        }

        @Override
        public void serialize(LongValueObject value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(value.asLong());
        }
    }

    public static class Deserializer extends StdDeserializer<LongValueObject> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public LongValueObject deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                           DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            return new LongValueObject(jsonParser.getIntValue(), jsonParser.getCurrentName());
        }
    }
}
