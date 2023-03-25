package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateStudierService {
    private final StudierRepository studierRepository;
    private final StringEntitiesRepository stringEntitiesRepository;
    public void updateStudierProfile(Id studierId, StudierDTO studierDTO) throws BusinessLogicException {
        Studier studier = studierRepository.findByStudierId(studierId);
        if(studier != null) {
            studier.setName(studierDTO.getName());
            studier.setGender(studierDTO.getGender());
            studier.setYob(studierDTO.getYob());
            studier.setAvatar(studierDTO.getAvatar());
            studier.setCoordinates(studierDTO.getCoordinates());
            studier.setDislikeIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.DISLIKE).findByValues(studierDTO.getDislikes())));
            studier.setLearningStyleIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.LEARNING_STYLE).findByValues(studierDTO.getLearningStyles())));
            studier.setLifeGoalIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.LIFE_GOAL).findByValues(studierDTO.getLifeGoals())));
            studier.setLikeIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.LIKE).findByValues(studierDTO.getLikes())));
            studier.setPersonalityIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.PERSONALITY).findByValues(studierDTO.getPersonalities())));
            studier.setQualificationIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.QUALIFICATION).findByValues(studierDTO.getQualifications())));
            studier.setMajorIds(StringEntity.extractIds(stringEntitiesRepository.find(StringEntities.Type.MAJOR).findByValues(studierDTO.getMajors())));
            studierRepository.save(studier);
        } else {
            throw new BusinessLogicException("Studier doesn't exist", "NOT_EXIST");
        }
    }

}
