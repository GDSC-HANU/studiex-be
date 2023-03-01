package com.gdsc.studiex.domain.suggestion.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.PastSuggestion;
import com.gdsc.studiex.domain.suggestion.repositories.PastSuggestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchPastSuggestionService {
    private PastSuggestionRepository pastSuggestionRepository;

    public PastSuggestion findByStudierId(Id studierId) {
        return pastSuggestionRepository.findByStudierId(studierId);
    }
}
