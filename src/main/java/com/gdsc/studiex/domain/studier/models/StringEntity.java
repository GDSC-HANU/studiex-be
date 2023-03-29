package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class StringEntity {
    private Id id;
    private String value;

    private StringEntity(Id id, String value) {
        this.id = id;
        this.value = value;
    }

    public static StringEntity create(String value) {
        return new StringEntity(
                Id.generateRandom(),
                value
        );
    }

    public static Set<Id> extractIds(List<StringEntity> entityList) {
        final Set<Id> ids = new HashSet<>();
        for (StringEntity entity : entityList)
            ids.add(entity.id);
        return ids;
    }
}
