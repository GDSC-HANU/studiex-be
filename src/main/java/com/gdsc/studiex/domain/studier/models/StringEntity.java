package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class StringEntity {
    private Id id;
    private String value;

    public static Set<Id> extractIds(List<StringEntity> entityList) {
        final Set<Id> ids = new HashSet<>();
        for (StringEntity entity : entityList)
            ids.add(entity.id);
        return ids;
    }
}
