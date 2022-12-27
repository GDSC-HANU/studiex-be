package com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

import java.io.IOException;

public class AllowedSupplyItemValueObjectMapper {
    public static class Deserializer extends StdDeserializer<AllowedSupplyItemValue> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public AllowedSupplyItemValue deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            final JsonNode jsonNode = jsonParser.readValueAsTree();
            if (jsonNode.isArray())
                return CustomObjectMapper.deserialize(jsonNode.toString(), AllowedSupplyItemArrayValue.class);
            if (jsonNode.has("minValue") && jsonNode.has("maxValue") && jsonNode.has("difference"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), AllowedSupplyItemRangeValue.class);
            throw new InvalidInputException("Invalid Allowed Supply Item Value");
        }
    }
}
