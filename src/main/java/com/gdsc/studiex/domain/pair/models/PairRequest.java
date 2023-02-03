package com.gdsc.studiex.domain.pair.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PairRequest {
    private Id fromStudierId;
    private Id toStudierId;
    private DateTime createdAt;

    private PairRequest() {
    }

    @Builder(builderMethodName = "newPairRequestBuilder", builderClassName = "NewPairRequestBuilder")
    public PairRequest(Id fromStudierId, Id toStudierId) {
        this.fromStudierId = fromStudierId;
        this.toStudierId = toStudierId;
        this.createdAt = DateTime.now();
    }
}
