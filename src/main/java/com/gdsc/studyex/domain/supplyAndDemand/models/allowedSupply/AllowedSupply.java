package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.IdentifiedDomainObject;
import lombok.Builder;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "allowedSupplies")
public class AllowedSupply extends IdentifiedDomainObject {
    @BsonProperty(value = "subjectName")
    private String subjectName;
    @BsonProperty(value = "allowedSupplyItems")
    private List<AllowedSupplyItem> allowedSupplyItems;

    @Builder(builderMethodName = "newAllowedSupplyBuilder", builderClassName = "NewAllowedSupplyBuilder")
    public AllowedSupply(String subjectName, List<AllowedSupplyItem> allowedSupplyItems) {
        super(Id.generateRandom());
        this.subjectName = subjectName;
        this.allowedSupplyItems = allowedSupplyItems;
    }
}
