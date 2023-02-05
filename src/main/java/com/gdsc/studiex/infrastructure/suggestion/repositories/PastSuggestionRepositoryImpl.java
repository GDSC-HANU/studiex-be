package com.gdsc.studiex.infrastructure.suggestion.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.PastSuggestion;
import com.gdsc.studiex.domain.suggestion.repositories.PastSuggestionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PastSuggestionRepositoryImpl implements PastSuggestionRepository {
    // TODO
    @Override
    public PastSuggestion getByStudierId(Id studierId) {
        return null;
    }
}
