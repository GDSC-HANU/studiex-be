package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

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

    public List<AllowedSupplyItem> getAllowedSupplyItems() {
        return Collections.unmodifiableList(allowedSupplyItems);
    }
}
