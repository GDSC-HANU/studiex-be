package com.gdsc.studiex.infrastructure.share.object_mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.gdsc.studiex.domain.share.models.DoubleValueObject;
import com.gdsc.studiex.domain.share.models.IntegerValueObject;
import com.gdsc.studiex.domain.share.models.LongValueObject;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomObjectMapper {
    private static final SimpleModule module = new SimpleModule();
    private static ObjectMapper instance = new ObjectMapper();

    static {
        addSerializer(IntegerValueObject.class, new IntegerValueObjectObjectMapper.Serializer());
        addDeserializer(IntegerValueObject.class, new IntegerValueObjectObjectMapper.Deserializer());

        addSerializer(DoubleValueObject.class, new DoubleValueObjectObjectMapper.Serializer());
        addDeserializer(DoubleValueObject.class, new DoubleValueObjectObjectMapper.Deserializer());

        addSerializer(LongValueObject.class, new LongValueObjectObjectMapper.Serializer());
        addDeserializer(LongValueObject.class, new LongValueObjectObjectMapper.Deserializer());

        addSerializer(StringValueObject.class, new StringValueObjectObjectMapper.Serializer());
        addDeserializer(StringValueObject.class, new StringValueObjectObjectMapper.Deserializer());

        addDeserializer(AllowedSupplyItemValue.class, new AllowedSupplyItemValueObjectMapper.Deserializer());
        addDeserializer(AllowedSupplyDTO.AllowedSupplyItemValueDTO.class, new AllowedSupplyItemValueDTOObjectMapper.Deserializer());

        addDeserializer(SupplyItemValue.class, new SupplyItemValueObjectMapper.Deserializer());
        addDeserializer(SuppliesDTO.SupplyItemValueDTO.class, new SupplyItemValueDTOObjectMapper.Deserializer());

        addDeserializer(DemandItemValue.class, new DemandItemValueObjectMapper.Deserializer());
        addDeserializer(DemandsDTO.DemandItemValueDTO.class, new DemandItemValueDTOObjectMapper.Deserializer());
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

    public static <T> T convertObjectClass(Object baseObject, Class<T> destinationClass) {
        return (T) deserialize(serialize(baseObject), destinationClass);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return instance();
    }
}
