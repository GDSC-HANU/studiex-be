package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.IdPool;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.*;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculateMatchScoreTest {
    @Test
    public void completelyMatches() {
        final Id[] idPool = IdPool.generateIdPool(20);
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
        Assertions.assertEquals(200, Suggestor.calculateMatchScore(supply, demand, 2));
    }

    @Test
    public void zeroMatches() {
        final Id[] idPool = IdPool.generateIdPool(21);
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
                                        .allowedSupplyItemArrayValueId(idPool[20])
                                        .build())
                                .description("")
                                .required(true)
                                .build(),
                        DemandItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[3])
                                .operator(DemandItemOperator.INCLUDES_ALL)
                                .value(DemandItemArrayValue.newDemandItemArrayValueBuilder()
                                        .allowedSupplyItemArrayValueIds(List.of(
                                                idPool[19],
                                                idPool[18]
                                        ))
                                        .build())
                                .description("")
                                .required(true)
                                .build(),
                        DemandItem.allArgsBuilder()
                                .allowedSupplyItemId(idPool[7])
                                .operator(DemandItemOperator.BETWEEN)
                                .value(DemandItemRangeValue.newDemandItemRangeValue()
                                        .minValue(101)
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
        Assertions.assertEquals(0, Suggestor.calculateMatchScore(supply, demand, 2));
    }

    @Test
    public void partialMatches() {
        final Id[] idPool = IdPool.generateIdPool(20);
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
                                        .allowedSupplyItemArrayValueId(idPool[19])
                                        .build())
                                .description("")
                                .required(false)
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
                                .required(false)
                                .build()
                ))
                .active(true)
                .priority(DemandPriority.HIGH)
                .customDemandItems(new ArrayList<>())
                .build();
        Assertions.assertEquals((double) 2 * 100 * 2 / 3, Suggestor.calculateMatchScore(supply, demand, 2));
    }
}
