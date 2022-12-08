package com.gdsc.studyex.domain.supply.models;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.IdentifiedDomainObject;

import java.util.List;

public class AllowedSupply extends IdentifiedDomainObject {
    private String subjectName;
    private List<AllowedSupplyItem> allowedSupplyItems;

    private AllowedSupply(Id id,
                          String subjectName,
                          List<AllowedSupplyItem> allowedSupplyItems) {
        super(id);
        this.subjectName = subjectName;
        this.allowedSupplyItems = allowedSupplyItems;
    }

    public static AllowedSupply create(String subjectName, List<AllowedSupplyItem> allowedSupplyItems) {
        return new AllowedSupply(
                Id.generateRandom(),
                subjectName,
                allowedSupplyItems
        );
    }
}
