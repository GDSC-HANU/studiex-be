package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Supply {
    private Id allowedSupplyId;
    private List<SupplyItem> items;
    private boolean active;
    private SupplyPriority priority;

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public Supply(Id allowedSupplyId,
                  List<SupplyItem> items,
                  boolean active,
                  SupplyPriority priority,
                  AllowedSupply allowedSupply) throws InvalidInputException {
        this.allowedSupplyId = allowedSupplyId;
        this.items = items;
        this.active = active;
        this.priority = priority;
        for (int i = 0; i < allowedSupply.getItems().size(); i++) {
            final AllowedSupplyItem allowedSupplyItem = allowedSupply.getItems().get(i);
            if (allowedSupplyItem.isRequired())
                if (findSupplyItemByAllowedSupplyItemIndex(i) == null)
                    throw new InvalidInputException("Lack of Required Allowed Suppy Item of key: " + allowedSupplyItem.getKey());
        }
        validate();
    }

    private SupplyItem findSupplyItemByAllowedSupplyItemIndex(int index) {
        for (SupplyItem item : items)
            if (item.getAllowedSupplyItemIndex() == index)
                return item;
        return null;
    }

    private void validate() throws InvalidInputException {
        if (allowedSupplyId == null)
            throw new InvalidInputException("Supply.allowedSupplyId must not be null");
        if (items == null)
            items = new ArrayList<>();
        if (priority == null)
            throw new InvalidInputException("Supply.priority must not be null");
    }

    public List<SupplyItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}