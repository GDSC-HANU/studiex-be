package com.gdsc.studyex.domain.supply.models;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.VersioningDomainObject;

import java.util.List;

public class Supply extends VersioningDomainObject {
    private Id studierId;
    private Id allowedSupplyId;
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
