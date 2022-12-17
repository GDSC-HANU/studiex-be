package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.share.models.VersioningDomainObject;
import com.gdsc.studyex.domain.supply_and_demand.models.supplies_quota.SuppliesQuota;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "supplies")
public class Supplies extends VersioningDomainObject {
    private Id studierId;
    private List<Supply> supplies;

    @Builder(builderMethodName = "newSuppliesBuilder", builderClassName = "NewSuppliesBuilder")
    public Supplies(Id studierId, List<Supply> supplies, SuppliesQuota suppliesQuota) throws InvalidInputException {
        super(0);
        this.studierId = studierId;
        this.supplies = supplies;
        if (supplies.size() > suppliesQuota.getMaxQuota() || supplies.size() < suppliesQuota.getMinQuota())
            throw new InvalidInputException("Invalid number of supplies, require min " + suppliesQuota.getMinQuota()
                    + ", require max " + suppliesQuota.getMaxQuota());
        validate();
    }

    private void validate() throws InvalidInputException {
        if (studierId == null)
            throw new InvalidInputException("Supplies.studierId must not be null");
        if (supplies == null)
            supplies = new ArrayList<>();
    }
}
