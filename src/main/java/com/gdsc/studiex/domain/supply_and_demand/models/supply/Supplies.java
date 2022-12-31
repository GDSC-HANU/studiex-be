package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
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
    public Supplies(Id studierId, List<Supply> supplies, SupplyAndDemandQuota supplyAndDemandQuota) throws InvalidInputException {
        this.studierId = studierId;
        this.supplies = supplies;
        if (countActiveSupplies() > supplyAndDemandQuota.getMaxActiveSupply())
            throw new InvalidInputException("Active Supplies exceed max active quota: " + supplyAndDemandQuota.getMaxActiveSupply());
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
