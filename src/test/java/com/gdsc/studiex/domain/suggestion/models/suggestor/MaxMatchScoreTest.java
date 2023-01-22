package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MaxMatchScoreTest {
    @Test
    public void hasMaxValue() {
        final List<Suggestor.OptimalSupplyDemandPair> optimalSupplyDemandPairs = List.of(
                Suggestor.OptimalSupplyDemandPair.builder()
                        .supply(null)
                        .demand(null)
                        .matchScore(10)
                        .build(),
                Suggestor.OptimalSupplyDemandPair.builder()
                        .supply(null)
                        .demand(null)
                        .matchScore(50)
                        .build(),
                Suggestor.OptimalSupplyDemandPair.builder()
                        .supply(null)
                        .demand(null)
                        .matchScore(30)
                        .build()
        );
        Assertions.assertEquals(50, Suggestor.maxMatchScore(optimalSupplyDemandPairs).getMatchScore());
    }
}
