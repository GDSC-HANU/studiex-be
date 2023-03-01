package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.PositiveNumber;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
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
    private PositiveNumber distance;
    private Set<Id> lifeGoalIds;
    private Set<Id> learningStyleIds;
    private Set<Id> majorIds;

    public StudierSearchCriteria(Set<Id> qualificationIds,
                                 AgeRange ageRange,
                                 Gender gender,
                                 Set<Id> personalityIds,
                                 Set<Id> likeIds,
                                 Set<Id> dislikeIds,
                                 PositiveNumber distance,
                                 Set<Id> lifeGoalIds,
                                 Set<Id> learningStyleIds,
                                 Set<Id> majorIds) {
        this.qualificationIds = Collections.unmodifiableSet(qualificationIds);
        this.ageRange = ageRange;
        this.gender = gender;
        this.personalityIds = Collections.unmodifiableSet(personalityIds);
        this.likeIds = Collections.unmodifiableSet(likeIds);
        this.dislikeIds = Collections.unmodifiableSet(dislikeIds);
        this.distance = distance;
        this.lifeGoalIds = Collections.unmodifiableSet(lifeGoalIds);
        this.learningStyleIds = Collections.unmodifiableSet(learningStyleIds);
        this.majorIds = Collections.unmodifiableSet(majorIds);
    }
}
