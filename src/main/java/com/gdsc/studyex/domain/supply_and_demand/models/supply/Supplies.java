package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.VersioningDomainObject;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "supplies")
public class Supplies extends VersioningDomainObject {
    private Id studierId;
    private List<Supply> supplies;

    @Builder(builderMethodName = "newSuppliesBuilder", builderClassName = "NewSuppliesBuilder")
    public Supplies(Id studierId, List<Supply> supplies) {
        super(0);
        this.studierId = studierId;
        this.supplies = supplies;
    }
}
