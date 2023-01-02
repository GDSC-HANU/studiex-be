package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.IdentifiedVersioningAggregateRoot;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AllowedSupply extends IdentifiedVersioningAggregateRoot {
    private String subjectName;
    private List<AllowedSupplyItem> allowedSupplyItems;

    @Builder(builderMethodName = "newAllowedSupplyBuilder", builderClassName = "NewAllowedSupplyBuilder")
    public AllowedSupply(String subjectName, List<AllowedSupplyItem> allowedSupplyItems) throws InvalidInputException {
        super(Id.generateRandom(), 0);
        this.subjectName = subjectName;
        this.allowedSupplyItems = allowedSupplyItems;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (subjectName == null)
            throw new InvalidInputException("AllowedSupplyItem.subjectName must not be null");
        if (allowedSupplyItems == null)
            allowedSupplyItems = new ArrayList<>();
    }

    public AllowedSupplyItem findItemByKey(String key) {
        for (AllowedSupplyItem item : allowedSupplyItems)
            if (item.getKey().equals(key))
                return item;
        return null;
    }

    public static AllowedSupply findAllowedSupplyBySubjectName(List<AllowedSupply> allowedSupplies, String subjectName) {
        for (AllowedSupply allowedSupply : allowedSupplies)
            if (allowedSupply.getSubjectName().equals(subjectName))
                return allowedSupply;
        return null;
    }
}
