package com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

import java.io.IOException;

public class SupplyItemValueDTOObjectMapper {
    public static class Deserializer extends StdDeserializer<SuppliesDTO.SupplyItemValueDTO> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public SuppliesDTO.SupplyItemValueDTO deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                           DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            final JsonNode jsonNode = jsonParser.readValueAsTree();
            if (jsonNode.isArray())
                return CustomObjectMapper.deserialize(jsonNode.toString(), SuppliesDTO.SupplyItemArrayValueDTO.class);
            if (jsonNode.isTextual())
                return CustomObjectMapper.deserialize(jsonNode.toString(), SuppliesDTO.SupplyItemExactValueDTO.class);
            if (jsonNode.has("minValue") && jsonNode.has("maxValue"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), SuppliesDTO.SupplyItemRangeValueDTO.class);
            throw new InvalidInputException("Invalid Supply Item Value");
        }
    }
}
