package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Supply {
    private Id allowedSupplyId;
    private List<SupplyItem> items;
    private boolean active;

    @Builder(builderMethodName = "newSupplyBuilder", builderClassName = "NewSupplyBuilder")
    public Supply(Id allowedSupplyId, List<SupplyItem> items, boolean active) throws InvalidInputException {
        this.allowedSupplyId = allowedSupplyId;
        this.items = items;
        this.active = active;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (allowedSupplyId == null)
            throw new InvalidInputException("Supply.allowedSupplyId must not be null");
        if (items == null)
            items = new ArrayList<>();
    }
}