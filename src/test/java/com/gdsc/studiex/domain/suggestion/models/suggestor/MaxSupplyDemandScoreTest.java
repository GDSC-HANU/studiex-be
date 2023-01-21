package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MaxSupplyDemandScoreTest {
    @Test
    public void hasMaxValue() {
        final List<Suggestor.MatchScore> matchScores = List.of(
                Suggestor.MatchScore.builder()
                        .supply(null)
                        .demand(null)
                        .score(10)
                        .build(),
                Suggestor.MatchScore.builder()
                        .supply(null)
                        .demand(null)
                        .score(50)
                        .build(),
                Suggestor.MatchScore.builder()
                        .supply(null)
                        .demand(null)
                        .score(30)
                        .build()
        );
        Assertions.assertEquals(50, Suggestor.maxSupplyDemandScore(matchScores).getScore());
    }
}
