package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;
import com.gdsc.studiex.domain.studier.models.StudierPrivacyDTO;
import com.gdsc.studiex.domain.studier.repositories.StudierPrivacyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateStudierPrivacyService {
    private final StudierPrivacyRepository studierPrivacyRepository;

    public void updateStudierPrivacy(Id studierId, StudierPrivacyDTO studierPrivacyDTO) throws BusinessLogicException {
        StudierPrivacy studierPrivacy = studierPrivacyRepository.findByStudierId(studierId);
        if(studierPrivacy != null) {
            studierPrivacy.setDislikes(studierPrivacyDTO.getDislikes());
            studierPrivacy.setGender(studierPrivacyDTO.getGender());
            studierPrivacy.setLearningStyles(studierPrivacyDTO.getLearningStyles());
            studierPrivacy.setLikes(studierPrivacyDTO.getLikes());
            studierPrivacy.setYob(studierPrivacyDTO.getYob());
            studierPrivacy.setMajors(studierPrivacyDTO.getMajors());
            studierPrivacy.setPersonalities(studierPrivacyDTO.getPersonalities());
            studierPrivacy.setQualifications(studierPrivacyDTO.getQualifications());
            studierPrivacy.setLifeGoals(studierPrivacyDTO.getLifeGoals());
            studierPrivacyRepository.save(studierPrivacy);
        } else {
            throw new BusinessLogicException("StudierPrivacy doesn't exist", "NOT_EXIST");
        }
    }
}
