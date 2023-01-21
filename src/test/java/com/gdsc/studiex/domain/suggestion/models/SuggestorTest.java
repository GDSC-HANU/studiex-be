package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.*;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestorTest {
    private Id[] generateIdPool(int size) {
        final Id[] result = new Id[size];
        for (int i = 0; i < size; i++)
            result[i] = Id.generateRandom();
        return result;
    }

    @Test
    public void calculateSupplyDemandScoreTest_completelyMatched() {
        final Id[] idPool = generateIdPool(20);
        final Supply supply = Supply.allArgsBuilder()
                .allowedSupplyId(idPool[0])
                .supplyItems(Arrays.asList(
                        SupplyItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[1])
                                .operator(SupplyItemOperator.IS)
                                .value(SupplyItemExactValue.newSupplyItemExactValueBuilder()
                                        .allowedSupplyItemArrayValueId(idPool[2])
                                        .build())
                                .description("")
                                .build(),
                        SupplyItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[3])
                                .operator(SupplyItemOperator.ARE)
                                .value(SupplyItemArrayValue.newSupplyItemArrayValueBuilder()
                                        .allowedSupplyItemArrayValueIds(List.of(
                                                idPool[4],
                                                idPool[5],
                                                idPool[6]
                                        ))
                                        .build())
                                .description("")
                                .build(),
                        SupplyItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[7])
                                .operator(SupplyItemOperator.BETWEEN)
                                .value(SupplyItemRangeValue.newSupplyItemRangeValue()
                                        .minValue(50)
                                        .maxValue(100)
                                        .build())
                                .description("")
                                .build()
                ))
                .active(true)
                .priority(SupplyPriority.HIGH)
                .customSupplyItems(new ArrayList<>())
                .build();
        final Demand demand = Demand.allArgsBuilder()
                .allowedSupplyId(idPool[0])
                .demandItems(Arrays.asList(
                        DemandItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[1])
                                .operator(DemandItemOperator.IS)
                                .value(DemandItemExactValue.newDemandItemExactValueBuilder()
                                        .allowedSupplyItemArrayValueId(idPool[2])
                                        .build())
                                .description("")
                                .required(true)
                                .build(),
                        DemandItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[3])
                                .operator(DemandItemOperator.INCLUDES_ALL)
                                .value(DemandItemArrayValue.newDemandItemArrayValueBuilder()
                                        .allowedSupplyItemArrayValueIds(List.of(
                                                idPool[4],
                                                idPool[6]
                                        ))
                                        .build())
                                .description("")
                                .required(true)
                                .build(),
                        DemandItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[7])
                                .operator(DemandItemOperator.BETWEEN)
                                .value(DemandItemRangeValue.newDemandItemRangeValue()
                                        .minValue(30)
                                        .maxValue(200)
                                        .build())
                                .description("")
                                .required(true)
                                .build()
                ))
                .active(true)
                .priority(DemandPriority.HIGH)
                .customDemandItems(new ArrayList<>())
                .build();
        Assertions.assertEquals(200, Suggestor.calculateSupplyDemandScore(supply, demand, 2));
    }
}
