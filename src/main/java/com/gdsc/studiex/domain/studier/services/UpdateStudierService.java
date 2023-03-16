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
    private final DislikeRepository dislikeRepository;
    private final LearningStyleRepository learningStyleRepository;
    private final LifeGoalRepository lifeGoalRepository;
    private final LikeRepository likeRepository;
    private final MajorRepository majorRepository;
    private final PersonalityRepository personalityRepository;
    private final QualificationRepository qualificationRepository;
    public void updateStudierProfile(Id studierId, StudierDTO studierDTO) throws BusinessLogicException {
        Studier studier = studierRepository.findByStudierId(studierId);
        if(studier != null) {
            studier.setName(studierDTO.getName());
            studier.setGender(studierDTO.getGender());
            studier.setYob(studierDTO.getYob());
            studier.setAvatar(studierDTO.getAvatar());
            studier.setCoordinates(studierDTO.getCoordinates());
            studier.setDislikeIds(StringEntity.extractIds(dislikeRepository.find().findByValues(studierDTO.getDislikes())));
            studier.setLearningStyleIds(StringEntity.extractIds(learningStyleRepository.find().findByValues(studierDTO.getLearningStyles())));
            studier.setLifeGoalIds(StringEntity.extractIds(lifeGoalRepository.find().findByValues(studierDTO.getLifeGoals())));
            studier.setLikeIds(StringEntity.extractIds(likeRepository.find().findByValues(studierDTO.getLikes())));
            studier.setPersonalityIds(StringEntity.extractIds(personalityRepository.find().findByValues(studierDTO.getPersonalities())));
            studier.setQualificationIds(StringEntity.extractIds(qualificationRepository.find().findByValues(studierDTO.getQualifications())));
            studier.setMajorIds(StringEntity.extractIds(majorRepository.find().findByValues(studierDTO.getMajors())));
            studierRepository.save(studier);
        } else {
            throw new BusinessLogicException("Studier doesn't exist", "NOT_EXIST");
        }
    }

}
