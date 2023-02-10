package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchStudierService {
    private final StudierRepository studierRepository;

    public Studier getStudier(Id studierId) {
        return studierRepository.findByStudierId(studierId);
    }
}
