package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculateScoreOfOptimalSupplyDemandTest {
    @Test
    public void resultTest() {
        double score = Suggestor.calculateScoreOfOptimalSupplyDemand(
                Suggestor.OptimalSupplyDemandPair.builder()
                        .matchScore(20)
                        .build(),
                Suggestor.OptimalSupplyDemandPair.builder()
                        .matchScore(50)
                        .build()
        );
        Assertions.assertEquals(
                (double) (50 + 20) / (50 - 20),
                score
        );
    }
}
