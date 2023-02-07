package com.gdsc.studiex.domain.suggestion.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.PastSuggestion;

public interface PastSuggestionRepository {
    public PastSuggestion findByStudierId(Id studierId);

    public void save(PastSuggestion pastSuggestion);
}
