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

    public void updateStudierProfile(Id studierId, StudierDTO studierDTO) {
        Studier studier = studierRepository.findByStudierId(studierId);
        if(studier != null) {
            studier.setName(studierDTO.getName());
            studier.setGender(studierDTO.getGender());
            studier.setYob(studierDTO.getYob());
            studier.setAvatar(studierDTO.getAvatar());
        }
        studierRepository.save(studier);
    }

}
