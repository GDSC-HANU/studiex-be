package com.gdsc.studiex.domain.pair.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Pair {
    private Id firstStudierId;
    private Id secondStudierId;
    private DateTime createdAt;

    private Pair() {
    }

    @Builder(builderMethodName = "newPairBuilder", builderClassName = "NewPairBuilder")
    public Pair(Id firstStudierId, Id secondStudierId) {
         this.firstStudierId = firstStudierId;
         this.secondStudierId = secondStudierId;
         this.createdAt = DateTime.now();
    }

}
