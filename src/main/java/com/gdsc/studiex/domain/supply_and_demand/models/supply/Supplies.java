package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.VersioningAggregateRoot;
import com.gdsc.studiex.domain.supply_and_demand.models.supplies_quota.SuppliesQuota;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Document(collection = "supplies")
public class Supplies {
    private Id studierId;
    private List<Supply> supplies;

    @Builder(builderMethodName = "newSuppliesBuilder", builderClassName = "NewSuppliesBuilder")
    public Supplies(Id studierId, List<Supply> supplies, SuppliesQuota suppliesQuota) throws InvalidInputException {
        this.studierId = studierId;
        this.supplies = supplies;
        if (countActiveSupplies() > suppliesQuota.getMaxActiveQuota())
            throw new InvalidInputException("Active Supplies exceed max active quota: " + suppliesQuota.getMaxActiveQuota());
        validate();
    }

    private int countActiveSupplies() {
        int result = 0;
        for (Supply supply : supplies)
            if (supply.isActive())
                result++;
        return result;
    }

    private void validate() throws InvalidInputException {
        if (studierId == null)
            throw new InvalidInputException("Supplies.studierId must not be null");
        if (supplies == null)
            supplies = new ArrayList<>();
    }

    public List<Supply> getSupplies() {
        return Collections.unmodifiableList(supplies);
    }
}
