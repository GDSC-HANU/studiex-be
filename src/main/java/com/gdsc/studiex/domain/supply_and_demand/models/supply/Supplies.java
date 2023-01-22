package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Supplies {
    private Id studierId;
    private List<Supply> supplies;

    // Only used for converting from repository / testing purpose
    @Builder(builderMethodName = "allArgsBuilder", builderClassName = "AllArgsBuilder")
    public Supplies(Id studierId, List<Supply> supplies) {
        this.studierId = studierId;
        this.supplies = supplies;
    }

    @Builder(builderMethodName = "newSuppliesBuilder", builderClassName = "NewSuppliesBuilder")
    public Supplies(Id studierId, List<Supply> supplies, SupplyAndDemandQuota supplyAndDemandQuota) throws InvalidInputException {
        this.studierId = studierId;
        this.supplies = supplies;
        if (countActiveSupplies() > supplyAndDemandQuota.getMaxActiveSupply())
            throw new InvalidInputException("Active Supplies exceed max active quota: " + supplyAndDemandQuota.getMaxActiveSupply());
        validate();
    }

    public Supply getSupplyByAllowedSupplyId(Id allowedSupplyId) {
        for (Supply supply : supplies)
            if (supply.getAllowedSupplyId().equals(allowedSupplyId))
                return supply;
        return null;
    }

    public List<Id> getAllowedSupplyIds() {
        return supplies.stream()
                .map(Supply::getAllowedSupplyId)
                .collect(Collectors.toList());
    }


    public static Supplies emptySupplies(Id studierId) {
        final Supplies supplies = new Supplies();
        supplies.studierId = studierId;
        supplies.supplies = new ArrayList<>();
        return supplies;
    }

    private Supplies() {
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
