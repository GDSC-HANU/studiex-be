package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateStringEntitiesService {
    private QualificationRepository qualificationRepository;
    private LifeGoalRepository lifeGoalRepository;
    private PersonalityRepository personalityRepository;
    private LearningStyleRepository learningStyleRepository;
    private MajorRepository majorRepository;
    private LikeRepository likeRepository;
    private DislikeRepository dislikeRepository;

    public void createStringEntities(List<String> strings, String type) {
        List<StringEntity> stringEntities = new ArrayList<>();
        for(String string : strings) {
            stringEntities.add(new StringEntity(Id.generateRandom(), string));
        }
        StringEntities entities = new StringEntities(stringEntities);
        switch (type.toLowerCase()) {
            case "qualification": qualificationRepository.save(entities); break;
            case "major": majorRepository.save(entities); break;
            case "like": likeRepository.save(entities); break;
            case "dislike": dislikeRepository.save(entities); break;
            case "personality": personalityRepository.save(entities); break;
            case "lifegoal": lifeGoalRepository.save(entities); break;
            case "learningstyle": learningStyleRepository.save(entities); break;
        }
    }
}
