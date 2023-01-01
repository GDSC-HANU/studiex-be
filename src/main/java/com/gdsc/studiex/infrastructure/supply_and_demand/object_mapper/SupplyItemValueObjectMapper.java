package com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemExactValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

import java.io.IOException;

public class SupplyItemValueObjectMapper {
    public static class Deserializer extends StdDeserializer<SupplyItemValue> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public SupplyItemValue deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                           DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            final JsonNode jsonNode = jsonParser.readValueAsTree();
            if (jsonNode.has("allowedSupplyItemArrayValueIds"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), SupplyItemArrayValue.class);
            if (jsonNode.has("allowedSupplyItemArrayValueId"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), SupplyItemExactValue.class);
            if (jsonNode.has("minValue") && jsonNode.has("maxValue"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), SupplyItemRangeValue.class);
            throw new InvalidInputException("Invalid Supply Item Value");
        }
    }
}
