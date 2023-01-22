package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
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
    private DemandPriority priority;
    private List<CustomDemandItem> customDemandItems;

    private Demand() {}

    // Only used for converting from repository / testing purpose
    @Builder(builderMethodName = "allArgsBuilder", builderClassName = "AllArgsBuilder")
    public Demand(Id allowedSupplyId, List<DemandItem> demandItems, boolean active, DemandPriority priority, List<CustomDemandItem> customDemandItems) {
        this.allowedSupplyId = allowedSupplyId;
        this.demandItems = demandItems;
        this.active = active;
        this.priority = priority;
        this.customDemandItems = customDemandItems;
        validate();
    }

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public Demand(List<DemandItem> demandItems,
                  boolean active,
                  DemandPriority priority,
                  List<CustomDemandItem> customDemandItems,
                  AllowedSupply allowedSupply) throws InvalidInputException {
        this.allowedSupplyId = allowedSupply.getId();
        this.demandItems = demandItems;
        this.active = active;
        this.priority = priority;
        this.customDemandItems = customDemandItems;
        validate();
    }

    public boolean matchAllRequiredDemandItems(Supply supply) {
        for (DemandItem demandItem : demandItems)
            if (demandItem.isRequired()) {
                boolean match = false;
                for (SupplyItem supplyItem : supply.getSupplyItems())
                    if (demandItem.match(supplyItem))
                        match = true;
                if (!match)
                    return false;
            }
        return true;
    }

    public int totalCriteria() {
        int result = 0;
        for (DemandItem demandItem : demandItems)
            result += demandItem.totalCriteria();
        return result;
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
