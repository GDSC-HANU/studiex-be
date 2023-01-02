package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Supply {
    private Id allowedSupplyId;
    private List<SupplyItem> supplyItems;
    private boolean active;
    private SupplyPriority priority;
    private List<CustomSupplyItem> customSupplyItems;

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public Supply(Id allowedSupplyId,
                  List<SupplyItem> supplyItems,
                  boolean active,
                  SupplyPriority priority,
                  List<CustomSupplyItem> customSupplyItems,
                  AllowedSupply allowedSupply) throws InvalidInputException {
        this.allowedSupplyId = allowedSupplyId;
        this.supplyItems = supplyItems;
        this.active = active;
        this.priority = priority;
        this.customSupplyItems = customSupplyItems;
        for (AllowedSupplyItem allowedSupplyItem : allowedSupply.getAllowedSupplyItems()) {
            if (allowedSupplyItem.isRequired() && findSupplyItemByAllowedSupplyItemId(allowedSupplyItem.getId()) == null)
                throw new InvalidInputException("Lack of Required Allowed Suppy Item of key: " + allowedSupplyItem.getKey());
        }
        validate();
    }

    private Supply() {
    }

    private SupplyItem findSupplyItemByAllowedSupplyItemId(Id allowedSupplyItemId) {
        for (SupplyItem item : supplyItems)
            if (item.getAllowedSupplyItemId() == allowedSupplyItemId)
                return item;
        return null;
    }

    private void validate() throws InvalidInputException {
        if (allowedSupplyId == null)
            throw new InvalidInputException("Supply.allowedSupplyId must not be null");
        if (supplyItems == null)
            supplyItems = new ArrayList<>();
        if (priority == null)
            throw new InvalidInputException("Supply.priority must not be null");
        if (customSupplyItems == null)
            customSupplyItems = new ArrayList<>();
    }

    public List<SupplyItem> getSupplyItems() {
        return Collections.unmodifiableList(supplyItems);
    }
}