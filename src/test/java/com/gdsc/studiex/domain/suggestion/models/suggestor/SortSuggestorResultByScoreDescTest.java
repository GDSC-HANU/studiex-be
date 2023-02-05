package com.gdsc.studiex.domain.suggestion.models.suggestor;

import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import com.gdsc.studiex.domain.suggestion.models.SuggestorResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortSuggestorResultByScoreDescTest {
    @Test
    public void sortTest() {
        final List<SuggestorResult> list = Arrays.asList(
                SuggestorResult.builder()
                        .score(5)
                        .build(),
                SuggestorResult.builder()
                        .score(10)
                        .build(),
                SuggestorResult.builder()
                        .score(8)
                        .build()
        );
        final List<SuggestorResult> sortedList = Suggestor.sortSuggestorResultByScoreDesc(list);
        Assertions.assertNotEquals(list, sortedList);
        Assertions.assertEquals(3, sortedList.size());
        Assertions.assertEquals(10, sortedList.get(0).getScore());
        Assertions.assertEquals(8, sortedList.get(1).getScore());
        Assertions.assertEquals(5, sortedList.get(2).getScore());
    }
}
