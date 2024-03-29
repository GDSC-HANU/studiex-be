package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class StringEntities {
    private List<StringEntity> data;
    private Type type;

    public StringEntities(List<StringEntity> data, Type type) {
        this.data = data;
    }

    public static StringEntities empty(Type type) {
        return new StringEntities(List.of(), type);
    }


    public Set<String> findStringsByIds(Set<Id> ids) {
        final Set<String> res = new HashSet<>();
        for (StringEntity entity : data)
            if (ids.contains(entity.getId()))
                res.add(entity.getValue());
        return res;
    }

    public List<StringEntity> findByValues(Set<String> values) {
        if (values == null)
            return new ArrayList<>();
        return data.stream()
                .filter(stringEntity -> values.contains(stringEntity.getValue()))
                .collect(Collectors.toList());
    }

    public List<StringEntity> findByIds(Set<Id> ids) {
        return data.stream()
                .filter(stringEntity -> ids.contains(stringEntity.getId()))
                .collect(Collectors.toList());
    }

    public void create(List<String> strings) {
        final List<String> unexistedStrings = removeExistedStrings(strings);
        for (String string : unexistedStrings)
            data.add(StringEntity.create(string));
    }

    public List<String> removeExistedStrings(List<String> strings) {
        final List<String> result = new ArrayList<>();
        for (String string : strings)
            if (!data
                    .stream()
                    .anyMatch(stringEntity -> stringEntity
                            .getValue()
                            .equals(string)))
                result.add(string);
        return result;
    }


    public enum Type {
        DISLIKE,
        LEARNING_STYLE,
        LIFE_GOAL,
        LIKE,
        MAJOR,
        PERSONALITY,
        QUALIFICATION,
    }
}
