package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Demand {
    private Id allowedSupplyId;
    private List<DemandItem> demandItems;
    private boolean active;
    private DemandItemPriority priority;
    private List<CustomDemandItem> customDemandItems;

    private Demand() {}

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public Demand(List<DemandItem> demandItems,
                  boolean active,
                  DemandItemPriority priority,
                  List<CustomDemandItem> customDemandItems,
                  AllowedSupply allowedSupply) throws InvalidInputException {
        this.allowedSupplyId = allowedSupply.getId();
        this.demandItems = demandItems;
        this.active = active;
        this.priority = priority;
        this.customDemandItems = customDemandItems;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (allowedSupplyId == null)
            throw new InvalidInputException("Demand.allowedSupplyId must not be null");
        if (demandItems == null)
            demandItems = new ArrayList<>();
        if (priority == null)
            throw new InvalidInputException("Demand.priority must not be null");
        if (customDemandItems == null)
            customDemandItems = new ArrayList<>();
    }

    public List<DemandItem> getDemandItems() {
        return Collections.unmodifiableList(demandItems);
    }
}
