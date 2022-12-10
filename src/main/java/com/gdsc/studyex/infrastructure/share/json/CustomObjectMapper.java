package com.gdsc.studyex.infrastructure.share.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.gdsc.studyex.domain.share.models.Id;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomObjectMapper {
    @Bean
    public ObjectMapper objectMapper() {
        return instance();
    }

    private static ObjectMapper instance = new ObjectMapper();
    private static final SimpleModule module = new SimpleModule();

    static {
        addSerializer(Id.class, new IdObjectMapper.Serializer());
        addDeserializer(Id.class, new IdObjectMapper.Deserializer());
    }

    private static void reset() {
        instance = new ObjectMapper();
        instance.registerModule(module);
        instance.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        instance.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static ObjectMapper instance() {
        return instance;
    }

    public static <T> void addSerializer(java.lang.Class<? extends T> type,
                                         com.fasterxml.jackson.databind.JsonSerializer<T> ser) {
        module.addSerializer(type, ser);
        reset();
    }

    public static <T> void addDeserializer(java.lang.Class<T> type,
                                           com.fasterxml.jackson.databind.JsonDeserializer<? extends T> deser) {
        module.addDeserializer(type, deser);
        reset();
    }

    public static String serialize(Object o) {
        try {
            return instance.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return instance.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}
