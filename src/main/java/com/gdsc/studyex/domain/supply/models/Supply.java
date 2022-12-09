package com.gdsc.studyex.domain.supply.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.VersioningDomainObject;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "supplies")
public class Supply extends VersioningDomainObject {
    @BsonProperty(value = "studierId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Id studierId;
    @BsonProperty(value = "allowedSupplyId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Id allowedSupplyId;
    @BsonProperty(value = "supplyItems")
    private List<SupplyItem> supplyItems;

    private Supply(Id studierId, Id allowedSupplyId, List<SupplyItem> supplyItems, long version) {
        super(version);
        this.studierId = studierId;
        this.allowedSupplyId = allowedSupplyId;
        this.supplyItems = supplyItems;
    }

    public static Supply create(Id studierId, Id allowedSupplyId, List<SupplyItem> supplyItems) {
        return new Supply(studierId,
                allowedSupplyId,
                supplyItems,
                0);
    }
}
