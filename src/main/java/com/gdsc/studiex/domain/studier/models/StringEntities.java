package com.gdsc.studiex.domain.studier.models;

import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class StringEntities {
    private List<StringEntity> data;

    private StringEntities(List<StringEntity> data) {
        this.data = data;
    }

    public static StringEntities empty() {
        return new StringEntities(List.of());
    }

    public List<StringEntity> findByValues(Set<String> values) {
        return data.stream()
                .filter(stringEntity -> values.contains(stringEntity.getValue()))
                .collect(Collectors.toList());
    }
}
