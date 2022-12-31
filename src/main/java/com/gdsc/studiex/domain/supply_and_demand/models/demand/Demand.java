package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Getter;

import java.util.List;

@Getter
public class Demand {
    private Id allowedSupplyId;
    private List<DemandItem> demandItems;
    private boolean active;
    private DemandPriority priority;
    private List<CustomDemandItem> customDemandItems;
}
