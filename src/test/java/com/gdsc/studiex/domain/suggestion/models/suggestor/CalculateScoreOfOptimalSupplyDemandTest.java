package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CalculateScoreOfOptimalSupplyDemandTest {
    @Test
    public void resultTest() {
        double score = Suggestor.calculateScoreMatchingSupplyDemandPairs(
                List.of(
                        Suggestor.MatchingSupplyDemandPair.builder()
                                .matchScore(20)
                                .build(),
                        Suggestor.MatchingSupplyDemandPair.builder()
                                .matchScore(20)
                                .build()
                ),
                List.of(
                        Suggestor.MatchingSupplyDemandPair.builder()
                                .matchScore(50)
                                .build(),
                        Suggestor.MatchingSupplyDemandPair.builder()
                                .matchScore(20)
                                .build(),
                        Suggestor.MatchingSupplyDemandPair.builder()
                                .matchScore(20)
                                .build()
                )
        );
        Assertions.assertEquals(
                (double) (40 + 90) / (90 - 40),
                score
        );
    }
}
