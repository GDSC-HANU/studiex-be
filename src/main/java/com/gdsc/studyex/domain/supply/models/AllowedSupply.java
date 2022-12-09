package com.gdsc.studyex.domain.supply.models;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.IdentifiedDomainObject;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "allowedSupplies")
public class AllowedSupply extends IdentifiedDomainObject {
    @BsonProperty(value = "subjectName")
    private String subjectName;
    @BsonProperty(value = "allowedSupplyItems")
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
