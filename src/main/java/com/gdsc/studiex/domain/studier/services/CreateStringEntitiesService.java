package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateStringEntitiesService {
    private StringEntitiesRepository stringEntitiesRepository;

    public void createStringEntities(List<String> strings, StringEntities.Type type) {
        final StringEntities stringEntities = stringEntitiesRepository.find(type);
        stringEntities.create(strings);
        stringEntitiesRepository.save(stringEntities);
    }
}
