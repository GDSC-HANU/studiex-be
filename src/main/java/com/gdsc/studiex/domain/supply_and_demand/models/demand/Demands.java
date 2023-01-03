package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Demands {
    private Id studierId;
    private List<Demand> demands;

    @Builder(builderMethodName = "newDemandsBuilder", builderClassName = "NewDemandsBuilder")
    public Demands(Id studierId, List<Demand> demands, SupplyAndDemandQuota supplyAndDemandQuota) {
        this.studierId = studierId;
        this.demands = demands;
        if (countActiveDemands() > supplyAndDemandQuota.getMaxActiveDemand())
            throw new InvalidInputException("Active Supplies exceed max active quota: " + supplyAndDemandQuota.getMaxActiveSupply());
        validate();
    }

    private void validate() throws InvalidInputException {
        if (studierId == null)
            throw new InvalidInputException("Supplies.studierId must not be null");
        if (demands == null)
            demands = new ArrayList<>();
    }

    private int countActiveDemands() {
        int result = 0;
        for (Demand demand : demands)
            if (demand.isActive())
                result++;
        return result;
    }

    public List<Demand> getDemands() {
        return Collections.unmodifiableList(demands);
    }
}
