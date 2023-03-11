package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.*;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final StudierPrivacyRepository studierPrivacyRepository;

    public StudierDTO getStudier(Id studierId, Id currentStudierId) throws BusinessLogicException {
        Studier studier = studierRepository.findByStudierId(studierId);
        if(studier == null) {
            throw new BusinessLogicException("Studier doesn't exist", "N0T_EXIST");
        }
        //for testing
        if (!studierId.equals(currentStudierId)) {
            return StudierDTO.builder().build();
        } else {
            StudierPrivacy studierPrivacy = studierPrivacyRepository.findByStudierId(studierId);
            PrivacyType genderPrivacy = studierPrivacy.getGender();
            PrivacyType yobPrivacy = studierPrivacy.getYob();
            PrivacyType qualificationPrivacy = studierPrivacy.getQualifications();
            PrivacyType dislikePrivacy = studierPrivacy.getDislikes();
            PrivacyType likePrivacy = studierPrivacy.getLikes();
            PrivacyType learningStylePrivacy = studierPrivacy.getLearningStyles();
            PrivacyType lifeGoalPrivacy = studierPrivacy.getLifeGoals();
            PrivacyType majorPrivacy = studierPrivacy.getMajors();
            PrivacyType personalityPrivacy = studierPrivacy.getPersonalities();
            PrivacyType coordinatesPrivacy = studierPrivacy.getCoordinates();

            List<StringEntity> qualificationEntities = qualificationRepository.findByIds(studier.getQualificationIds());
            List<StringEntity> dislikeEntities = dislikeRepository.findByIds(studier.getDislikeIds());
            List<StringEntity> likeEntities = likeRepository.findByIds(studier.getLikeIds());
            List<StringEntity> learningStyleEntities = learningStyleRepository.findByIds(studier.getLearningStyleIds());
            List<StringEntity> majorEntities = majorRepository.findByIds(studier.getMajorIds());
            List<StringEntity> personalityEntities = personalityRepository.findByIds(studier.getPersonalityIds());
            List<StringEntity> lifeGoalEntities = lifeGoalRepository.findByIds(studier.getLifeGoalIds());
            return StudierDTO.builder()
                    .name(studier.getName())
                    .gender(genderPrivacy == PrivacyType.PUBLIC ? studier.getGender() : null)
                    .yob(yobPrivacy == PrivacyType.PUBLIC ? studier.getYob() : null)
                    .avatar(studier.getAvatar())
                    .qualifications(qualificationPrivacy == PrivacyType.PUBLIC ?
                            qualificationEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .dislikes(dislikePrivacy == PrivacyType.PUBLIC ?
                            dislikeEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .likes(likePrivacy == PrivacyType.PUBLIC ?
                            likeEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .learningStyles(learningStylePrivacy == PrivacyType.PUBLIC ?
                            learningStyleEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .lifeGoals(lifeGoalPrivacy == PrivacyType.PUBLIC ?
                            lifeGoalEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .majors(majorPrivacy == PrivacyType.PUBLIC ?
                            majorEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .coordinates(coordinatesPrivacy == PrivacyType.PUBLIC ? studier.getCoordinates() : null)
                    .personalities(personalityPrivacy == PrivacyType.PUBLIC ?
                            personalityEntities.stream().map(StringEntity::getValue).collect(Collectors.toUnmodifiableSet()) : null)
                    .build();
        }
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
