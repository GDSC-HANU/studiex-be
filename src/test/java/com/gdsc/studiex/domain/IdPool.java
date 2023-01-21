package com.gdsc.studiex.domain;

import com.gdsc.studiex.domain.share.models.Id;

public class IdPool {
    public static Id[] generateIdPool(int size) {
        final Id[] result = new Id[size];
        for (int i = 0; i < size; i++)
            result[i] = Id.generateRandom();
        return result;
    }
}
