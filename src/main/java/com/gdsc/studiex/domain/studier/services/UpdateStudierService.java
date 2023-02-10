package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateStudierService {
    private final StudierRepository studierRepository;

    public void updateStudierProfile(Studier studier) {
        studierRepository.save(studier);
    }

    public static Studier buildStudier(Id studierId, StudierDTO studierDTO) {
        return Studier.createStudierWithId(studierId,
                studierDTO.getName(),
                studierDTO.getGender(),
                studierDTO.getYob(),
                studierDTO.getAvatar(),
                studierDTO.getFacebookLink());
    }
}
