package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteria;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteriaDTO;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchStudierService {
    private final StudierRepository studierRepository;
    private final DislikeRepository dislikeRepository;
    private final LearningStyleRepository learningStyleRepository;
    private final LifeGoalRepository lifeGoalRepository;
    private final LikeRepository likeRepository;
    private final MajorRepository majorRepository;
    private final PersonalityRepository personalityRepository;
    private final QualificationRepository qualificationRepository;

    public Studier getStudier(Id studierId) {
        return studierRepository.findByStudierId(studierId);
    }

    public List<Studier> searchByCriteria(Id studierId, StudierSearchCriteriaDTO studierSearchCriteria) {
        final List<StringEntity> qualifications = qualificationRepository.find().findByValues(studierSearchCriteria.getQualifications());
        final List<StringEntity> personalities = personalityRepository.find().findByValues(studierSearchCriteria.getPersonalities());
        final List<StringEntity> likes = likeRepository.find().findByValues(studierSearchCriteria.getLikes());
        final List<StringEntity> dislikes = dislikeRepository.find().findByValues(studierSearchCriteria.getDislikes());
        final List<StringEntity> lifeGoals = lifeGoalRepository.find().findByValues(studierSearchCriteria.getLifeGoals());
        final List<StringEntity> learningStyles = learningStyleRepository.find().findByValues(studierSearchCriteria.getLearningStyles());
        final List<StringEntity> majors = majorRepository.find().findByValues(studierSearchCriteria.getMajors());
        final List<Studier> studiers = studierRepository.searchByCriteria(
                studierId,
                StudierSearchCriteria.builder()
                        .qualificationIds(StringEntity.extractIds(qualifications))
                        .ageRange(studierSearchCriteria.getAgeRange())
                        .gender(studierSearchCriteria.getGender())
                        .personalityIds(StringEntity.extractIds(personalities))
                        .likeIds(StringEntity.extractIds(likes))
                        .dislikeIds(StringEntity.extractIds(dislikes))
                        .distance(studierSearchCriteria.getDistance())
                        .lifeGoalIds(StringEntity.extractIds(lifeGoals))
                        .learningStyleIds(StringEntity.extractIds(learningStyles))
                        .majorIds(StringEntity.extractIds(majors))
                        .build()
        );
        return studiers;
    }
}
