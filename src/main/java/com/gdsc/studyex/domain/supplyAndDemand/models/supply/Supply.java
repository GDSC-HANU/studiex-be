package com.gdsc.studyex.domain.supplyAndDemand.models.supply;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gdsc.studyex.domain.share.models.Id;
import lombok.Builder;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class Supply {
    @BsonProperty(value = "allowedSupplyId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Id allowedSupplyId;
    @BsonProperty(value = "supplyItems")
    private List<SupplyItem> supplyItems;
    private boolean active;

    @Builder(builderMethodName = "newSupplyBuilder", builderClassName = "NewSupplyBuilder")
    public Supply(Id allowedSupplyId, List<SupplyItem> supplyItems, boolean active) {
        this.allowedSupplyId = allowedSupplyId;
        this.supplyItems = supplyItems;
        this.active = active;
    }
}
