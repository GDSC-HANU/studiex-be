package com.gdsc.studiex.infrastructure.supply_and_demand.object_mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

import java.io.IOException;

public class DemandItemValueDTOObjectMapper {
    public static class Deserializer extends StdDeserializer<DemandsDTO.DemandItemValueDTO> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public DemandsDTO.DemandItemValueDTO deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                                         DeserializationContext deserializationContext) throws
                IOException, JacksonException {
            final JsonNode jsonNode = jsonParser.readValueAsTree();
            if (jsonNode.isArray())
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandsDTO.DemandItemArrayValueDTO.class);
            if (jsonNode.isTextual())
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandsDTO.DemandItemExactValueDTO.class);
            if (jsonNode.has("minValue") && jsonNode.has("maxValue"))
                return CustomObjectMapper.deserialize(jsonNode.toString(), DemandsDTO.DemandItemRangeValueDTO.class);
            throw new InvalidInputException("Invalid Demand Item Value");
        }
    }
}
