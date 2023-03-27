package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.Pair;
import com.gdsc.studiex.domain.studier.models.*;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchStudierService {
    private final StudierRepository studierRepository;
    private final StringEntitiesRepository stringEntitiesRepository;
    private final StudierPrivacyRepository studierPrivacyRepository;

    public StudierDTO getStudier(Id studierId, Id currentStudierId) throws BusinessLogicException {
        return getStudierWithDTO(studierId, currentStudierId).getFirst();
    }

    public List<Pair<StudierDTO, Studier>> getStudiersWithDTO(List<Id> studierIds) {
        final List<Studier> studiers = studierRepository.findByStudierIds(studierIds);
        final List<StudierPrivacy> studierPrivacies = studierPrivacyRepository.findByStudierIds(studierIds);

        StringEntities qualificationEntities = stringEntitiesRepository.find(StringEntities.Type.QUALIFICATION);
        StringEntities dislikeEntities = stringEntitiesRepository.find(StringEntities.Type.DISLIKE);
        StringEntities likeEntities = stringEntitiesRepository.find(StringEntities.Type.LIKE);
        StringEntities learningStyleEntities = stringEntitiesRepository.find(StringEntities.Type.LEARNING_STYLE);
        StringEntities majorEntities = stringEntitiesRepository.find(StringEntities.Type.MAJOR);
        StringEntities personalityEntities = stringEntitiesRepository.find(StringEntities.Type.PERSONALITY);
        StringEntities lifeGoalEntities = stringEntitiesRepository.find(StringEntities.Type.LIFE_GOAL);

        final List<Pair<StudierDTO, Studier>> result = new ArrayList<>();
        for (Studier studier : studiers)
            for (StudierPrivacy studierPrivacy : studierPrivacies)
                if (studier.getStudierId().equals(studierPrivacy.getStudierId())) {
                    result.add(new Pair<>(
                            StudierDTO.withPrivacy()
                                    .studierPrivacy(studierPrivacy)
                                    .name(studier.getName())
                                    .gender(studier.getGender())
                                    .yob(studier.getYob())
                                    .avatar(studier.getAvatar())
                                    .qualifications(qualificationEntities.findStringsByIds(studier.getQualificationIds()))
                                    .personalities(personalityEntities.findStringsByIds(studier.getPersonalityIds()))
                                    .likes(likeEntities.findStringsByIds(studier.getLikeIds()))
                                    .dislikes(dislikeEntities.findStringsByIds(studier.getDislikeIds()))
                                    .coordinates(studier.getCoordinates())
                                    .lifeGoals(lifeGoalEntities.findStringsByIds(studier.getLifeGoalIds()))
                                    .learningStyles(learningStyleEntities.findStringsByIds(studier.getLearningStyleIds()))
                                    .majors(majorEntities.findStringsByIds(studier.getMajorIds()))
                                    .build(),
                            studier
                    ));
                }

        return result;
    }

    public Pair<StudierDTO, Studier> getStudierWithDTO(Id studierId, Id currentStudierId) throws BusinessLogicException {
        Studier studier = studierRepository.findByStudierId(studierId);
        if (studier == null) {
            throw new BusinessLogicException("Studier doesn't exist", "NOT_EXIST");
        }

        StringEntities qualificationEntities = stringEntitiesRepository.find(StringEntities.Type.QUALIFICATION);
        StringEntities dislikeEntities = stringEntitiesRepository.find(StringEntities.Type.DISLIKE);
        StringEntities likeEntities = stringEntitiesRepository.find(StringEntities.Type.LIKE);
        StringEntities learningStyleEntities = stringEntitiesRepository.find(StringEntities.Type.LEARNING_STYLE);
        StringEntities majorEntities = stringEntitiesRepository.find(StringEntities.Type.MAJOR);
        StringEntities personalityEntities = stringEntitiesRepository.find(StringEntities.Type.PERSONALITY);
        StringEntities lifeGoalEntities = stringEntitiesRepository.find(StringEntities.Type.LIFE_GOAL);


        if (studierId.equals(currentStudierId)) {
            return new Pair<>(
                    StudierDTO.withPrivacy()
                            .studierPrivacy(StudierPrivacy.allPublic(studierId))
                            .name(studier.getName())
                            .gender(studier.getGender())
                            .yob(studier.getYob())
                            .avatar(studier.getAvatar())
                            .qualifications(qualificationEntities.findStringsByIds(studier.getQualificationIds()))
                            .personalities(personalityEntities.findStringsByIds(studier.getPersonalityIds()))
                            .likes(likeEntities.findStringsByIds(studier.getLikeIds()))
                            .dislikes(dislikeEntities.findStringsByIds(studier.getDislikeIds()))
                            .coordinates(studier.getCoordinates())
                            .lifeGoals(lifeGoalEntities.findStringsByIds(studier.getLifeGoalIds()))
                            .learningStyles(learningStyleEntities.findStringsByIds(studier.getLearningStyleIds()))
                            .majors(majorEntities.findStringsByIds(studier.getMajorIds()))
                            .build(),
                    studier
            );
        }

        StudierPrivacy studierPrivacy = studierPrivacyRepository.findByStudierId(studierId);
        return new Pair<>(
                StudierDTO.withPrivacy()
                        .studierPrivacy(studierPrivacy)
                        .name(studier.getName())
                        .gender(studier.getGender())
                        .yob(studier.getYob())
                        .avatar(studier.getAvatar())
                        .qualifications(qualificationEntities.findStringsByIds(studier.getQualificationIds()))
                        .personalities(personalityEntities.findStringsByIds(studier.getPersonalityIds()))
                        .likes(likeEntities.findStringsByIds(studier.getLikeIds()))
                        .dislikes(dislikeEntities.findStringsByIds(studier.getDislikeIds()))
                        .coordinates(studier.getCoordinates())
                        .lifeGoals(lifeGoalEntities.findStringsByIds(studier.getLifeGoalIds()))
                        .learningStyles(learningStyleEntities.findStringsByIds(studier.getLearningStyleIds()))
                        .majors(majorEntities.findStringsByIds(studier.getMajorIds()))
                        .build(),
                studier
        );
    }

    public List<Studier> searchByCriteria(Id studierId, StudierSearchCriteriaDTO studierSearchCriteria) {
        final List<StringEntity> qualifications = stringEntitiesRepository.find(StringEntities.Type.QUALIFICATION).findByValues(studierSearchCriteria.getQualifications());
        final List<StringEntity> personalities = stringEntitiesRepository.find(StringEntities.Type.PERSONALITY).findByValues(studierSearchCriteria.getPersonalities());
        final List<StringEntity> likes = stringEntitiesRepository.find(StringEntities.Type.LIKE).findByValues(studierSearchCriteria.getLikes());
        final List<StringEntity> dislikes = stringEntitiesRepository.find(StringEntities.Type.DISLIKE).findByValues(studierSearchCriteria.getDislikes());
        final List<StringEntity> lifeGoals = stringEntitiesRepository.find(StringEntities.Type.LIFE_GOAL).findByValues(studierSearchCriteria.getLifeGoals());
        final List<StringEntity> learningStyles = stringEntitiesRepository.find(StringEntities.Type.LEARNING_STYLE).findByValues(studierSearchCriteria.getLearningStyles());
        final List<StringEntity> majors = stringEntitiesRepository.find(StringEntities.Type.MAJOR).findByValues(studierSearchCriteria.getMajors());
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
