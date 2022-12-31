package com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota;

import com.gdsc.studiex.domain.share.models.DateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SupplyAndDemandQuota {
    private int maxActiveSupply;
    private int maxActiveDemand;
    private DateTime createdAt;

    @Builder(builderMethodName = "newSuppliesQuota", builderClassName = "NewSuppliesQuota")
    public SupplyAndDemandQuota(int maxActiveSupply, int maxActiveDemand) {
        this.maxActiveSupply = maxActiveSupply;
        this.maxActiveDemand = maxActiveDemand;
        this.createdAt = DateTime.now();
    }
}
