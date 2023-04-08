package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.PositiveNumber;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class StudierSearchCriteria {
    private Set<Id> qualificationIds;
    private AgeRange ageRange;
    private Gender gender;
    private Set<Id> personalityIds;
    private Set<Id> likeIds;
    private Set<Id> dislikeIds;
    private Kilometer distance;
    private Set<Id> lifeGoalIds;
    private Set<Id> learningStyleIds;
    private Set<Id> majorIds;
    private Set<Language> languagesForCommunication;


    public StudierSearchCriteria(Set<Id> qualificationIds,
                                 AgeRange ageRange,
                                 Gender gender,
                                 Set<Id> personalityIds,
                                 Set<Id> likeIds,
                                 Set<Id> dislikeIds,
                                 Kilometer distance,
                                 Set<Id> lifeGoalIds,
                                 Set<Id> learningStyleIds,
                                 Set<Id> majorIds,
                                 Set<Language> languagesForCommunication) {
        this.qualificationIds = qualificationIds == null ? null : Collections.unmodifiableSet(qualificationIds);
        this.ageRange = ageRange;
        this.gender = gender;
        this.personalityIds = personalityIds == null ? null : Collections.unmodifiableSet(personalityIds);
        this.likeIds = likeIds == null ? null : Collections.unmodifiableSet(likeIds);
        this.dislikeIds = dislikeIds == null ? null : Collections.unmodifiableSet(dislikeIds);
        this.distance = distance;
        this.lifeGoalIds = lifeGoalIds == null ? null : Collections.unmodifiableSet(lifeGoalIds);
        this.learningStyleIds = learningStyleIds == null ? null : Collections.unmodifiableSet(learningStyleIds);
        this.majorIds = majorIds == null ? null : Collections.unmodifiableSet(majorIds);
        this.languagesForCommunication = languagesForCommunication == null ? null : Collections.unmodifiableSet(languagesForCommunication);
        if (languagesForCommunication == null || languagesForCommunication.isEmpty())
            throw new InvalidInputException("Must specify at least one language for communication");
    }
}
