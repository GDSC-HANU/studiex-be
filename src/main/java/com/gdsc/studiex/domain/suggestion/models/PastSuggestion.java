package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;

import java.util.ArrayList;
import java.util.List;

public class PastSuggestion {
    public static class Suggestion {
        private Id studierId;
        private DateTime suggestedAt;
    }
    private Id studierId;
    private List<Suggestion> suggestions;
    public List<Id> studierIds() {
        // TODO
        return new ArrayList<>();
    }
}
