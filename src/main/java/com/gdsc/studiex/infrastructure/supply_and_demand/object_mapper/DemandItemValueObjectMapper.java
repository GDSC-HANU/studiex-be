package com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemExactValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

import java.io.IOException;

public class DemandItemValueObjectMapper {
    public static class Deserializer extends StdDeserializer<DemandItemValue> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public DemandItemValue deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                           DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            final JsonNode jsonNode = jsonParser.readValueAsTree();
            if (jsonNode.has("allowedSupplyItemArrayValueIds"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandItemArrayValue.class);
            if (jsonNode.has("allowedSupplyItemArrayValueId"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandItemExactValue.class);
            if (jsonNode.has("minValue") && jsonNode.has("maxValue"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandItemRangeValue.class);
            throw new InvalidInputException("Invalid Demand Item Value");
        }
    }
}
