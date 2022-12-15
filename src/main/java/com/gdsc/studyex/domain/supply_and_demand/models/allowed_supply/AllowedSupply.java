package com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.IdentifiedVersioningDomainObject;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Document(collection = "allowedSupplies")
@Getter
public class AllowedSupply extends IdentifiedVersioningDomainObject {
    private String subjectName;
    private List<AllowedSupplyItem> allowedSupplyItems;

    @Builder(builderMethodName = "newAllowedSupplyBuilder", builderClassName = "NewAllowedSupplyBuilder")
    public AllowedSupply(String subjectName, List<AllowedSupplyItem> allowedSupplyItems) {
        super(Id.generateRandom(), 0);
        this.subjectName = subjectName;
        this.allowedSupplyItems = allowedSupplyItems;
    }

    private AllowedSupply(Id id, long version, String subjectName, List<AllowedSupplyItem> allowedSupplyItems) {
        super(id, version);
        this.subjectName = subjectName;
        this.allowedSupplyItems = allowedSupplyItems;
    }
}
