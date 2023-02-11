package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.IdPool;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.Pair;
import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.*;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMatchingSupplyDemandPairTest {
    @Test
    public void optimalSupplyDemandPair() {
        final Id[] studierIdPool = IdPool.generateIdPool(100);
        final Id[] allowedSupplyIdPool = IdPool.generateIdPool(100);
        final Id[] allowedSupplyItemIdPool = IdPool.generateIdPool(100);
        final Id[] allowedSupplyItemValueIdPool = IdPool.generateIdPool(100);
        final Supplies supplies = Supplies.allArgsBuilder()
                .studierId(studierIdPool[0])
                .supplies(Arrays.asList(
                        Supply.allArgsBuilder()
                                .allowedSupplyId(allowedSupplyIdPool[0])
                                .supplyItems(Arrays.asList(
                                        SupplyItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[0])
                                                .operator(SupplyItemOperator.IS)
                                                .value(SupplyItemExactValue.newSupplyItemExactValueBuilder()
                                                        .allowedSupplyItemArrayValueId(allowedSupplyItemValueIdPool[0])
                                                        .build())
                                                .description("")
                                                .build(),
                                        SupplyItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[1])
                                                .operator(SupplyItemOperator.ARE)
                                                .value(SupplyItemArrayValue.newSupplyItemArrayValueBuilder()
                                                        .allowedSupplyItemArrayValueIds(List.of(
                                                                allowedSupplyItemValueIdPool[1],
                                                                allowedSupplyItemValueIdPool[2],
                                                                allowedSupplyItemValueIdPool[3]
                                                        ))
                                                        .build())
                                                .description("")
                                                .build(),
                                        SupplyItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[2])
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
                                .build(),
                        Supply.allArgsBuilder()
                                .allowedSupplyId(allowedSupplyIdPool[1])
                                .supplyItems(Arrays.asList(
                                        SupplyItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[3])
                                                .operator(SupplyItemOperator.IS)
                                                .value(SupplyItemExactValue.newSupplyItemExactValueBuilder()
                                                        .allowedSupplyItemArrayValueId(allowedSupplyItemValueIdPool[4])
                                                        .build())
                                                .description("")
                                                .build(),
                                        SupplyItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[4])
                                                .operator(SupplyItemOperator.ARE)
                                                .value(SupplyItemArrayValue.newSupplyItemArrayValueBuilder()
                                                        .allowedSupplyItemArrayValueIds(List.of(
                                                                allowedSupplyItemValueIdPool[5],
                                                                allowedSupplyItemValueIdPool[6],
                                                                allowedSupplyItemValueIdPool[7]
                                                        ))
                                                        .build())
                                                .description("")
                                                .build()
                                ))
                                .active(true)
                                .priority(SupplyPriority.MEDIUM)
                                .customSupplyItems(new ArrayList<>())
                                .build()
                ))
                .build();
        final Demands demands = Demands.allArgsBuilder()
                .studierId(studierIdPool[1])
                .demands(Arrays.asList(
                        Demand.allArgsBuilder()
                                .allowedSupplyId(allowedSupplyIdPool[0])
                                .demandItems(Arrays.asList(
                                        DemandItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[0])
                                                .operator(DemandItemOperator.IS)
                                                .value(DemandItemExactValue.newDemandItemExactValueBuilder()
                                                        .allowedSupplyItemArrayValueId(allowedSupplyItemValueIdPool[0])
                                                        .build())
                                                .description("")
                                                .required(true)
                                                .build(),
                                        DemandItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[1])
                                                .operator(DemandItemOperator.INCLUDES_ALL)
                                                .value(DemandItemArrayValue.newDemandItemArrayValueBuilder()
                                                        .allowedSupplyItemArrayValueIds(List.of(
                                                                allowedSupplyItemIdPool[1],
                                                                allowedSupplyItemIdPool[3]
                                                        ))
                                                        .build())
                                                .description("")
                                                .required(true)
                                                .build(),
                                        DemandItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[2])
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
                                .priority(DemandPriority.MEDIUM)
                                .customDemandItems(new ArrayList<>())
                                .build(),
                        Demand.allArgsBuilder()
                                .allowedSupplyId(allowedSupplyIdPool[1])
                                .demandItems(Arrays.asList(
                                        DemandItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[3])
                                                .operator(DemandItemOperator.IS)
                                                .value(DemandItemExactValue.newDemandItemExactValueBuilder()
                                                        .allowedSupplyItemArrayValueId(allowedSupplyItemValueIdPool[10])
                                                        .build())
                                                .description("")
                                                .required(true)
                                                .build(),
                                        DemandItem.allArgsBuilder()
                                                .allowedSupplyItemId(allowedSupplyItemIdPool[1])
                                                .operator(DemandItemOperator.INCLUDES_ANY)
                                                .value(DemandItemArrayValue.newDemandItemArrayValueBuilder()
                                                        .allowedSupplyItemArrayValueIds(List.of(
                                                                allowedSupplyItemIdPool[7],
                                                                allowedSupplyItemIdPool[9]
                                                        ))
                                                        .build())
                                                .description("")
                                                .required(true)
                                                .build()
                                ))
                                .active(true)
                                .priority(DemandPriority.LOW)
                                .customDemandItems(new ArrayList<>())
                                .build()
                ))
                .build();
        final List<Suggestor.MatchingSupplyDemandPair> matchingSupplyDemandPairs = Suggestor.findMatchingSupplyDemandPairs(supplies, demands);
        Assertions.assertEquals(2, matchingSupplyDemandPairs.size());
        for (Suggestor.MatchingSupplyDemandPair matchingSupplyDemandPair : matchingSupplyDemandPairs) {
            if (matchingSupplyDemandPair.getDemand().getAllowedSupplyId().equals(allowedSupplyIdPool[0])) {
                Assertions.assertEquals(allowedSupplyIdPool[0], matchingSupplyDemandPair.getSupply().getAllowedSupplyId());
                Assertions.assertEquals(
                        Suggestor.calculateMatchScore(
                                supplies.getSupplyByAllowedSupplyId(allowedSupplyIdPool[0]),
                                demands.getDemandByAllowedSupplyId(allowedSupplyIdPool[0]),
                                Suggestor.getWeight(new Pair<>(DemandPriority.HIGH, SupplyPriority.MEDIUM))
                        ),
                        matchingSupplyDemandPair.getMatchScore()
                );
            } else if (matchingSupplyDemandPair.getDemand().getAllowedSupplyId().equals(allowedSupplyIdPool[1])) {
                Assertions.assertEquals(allowedSupplyIdPool[1], matchingSupplyDemandPair.getSupply().getAllowedSupplyId());
                Assertions.assertEquals(
                        Suggestor.calculateMatchScore(
                                supplies.getSupplyByAllowedSupplyId(allowedSupplyIdPool[1]),
                                demands.getDemandByAllowedSupplyId(allowedSupplyIdPool[1]),
                                Suggestor.getWeight(new Pair<>(DemandPriority.MEDIUM, SupplyPriority.LOW))
                        ),
                        matchingSupplyDemandPair.getMatchScore()
                );
            }
        }
    }
}
