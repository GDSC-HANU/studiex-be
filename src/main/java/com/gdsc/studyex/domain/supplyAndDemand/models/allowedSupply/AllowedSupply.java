package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.IdentifiedVersioningDomainObject;
import lombok.Builder;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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

    public List<AllowedSupplyItem> getAllowedSupplyItems() {
        return Collections.unmodifiableList(allowedSupplyItems);
    }
}
