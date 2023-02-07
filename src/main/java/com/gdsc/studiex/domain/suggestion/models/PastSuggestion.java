package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PastSuggestion {
    private Id studierId;
    private List<Suggestion> suggestions;

    public List<Id> studierIds() {
        return suggestions.stream()
                .map(Suggestion::getStudierId)
                .collect(Collectors.toList());
    }

    public void addStudierIds(List<Id> studierIds) {
        for (Id studierId : studierIds)
            suggestions.add(new Suggestion(studierId, DateTime.now()));
    }

    public List<Suggestion> getSuggestions() {
        return Collections.unmodifiableList(suggestions);
    }

    @Getter
    public static class Suggestion {
        private Id studierId;
        private DateTime suggestedAt;

        private Suggestion(Id studierId, DateTime suggestedAt) {
            this.studierId = studierId;
            this.suggestedAt = suggestedAt;
        }
    }
}
