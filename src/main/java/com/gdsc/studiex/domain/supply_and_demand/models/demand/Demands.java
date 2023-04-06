package com.gdsc.studiex.domain.supply_and_demand.models.demand;

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
public class Demands {
    private Id studierId;
    private List<Demand> demands;

    public static Demands emptyDemands(Id studierId) {
        return new Demands(
                studierId,
                new ArrayList<>()
        );
    }

    private Demands() {
    }

    @Builder(builderMethodName = "allArgsBuilder", builderClassName = "AllArgsBuilder")
    public Demands(Id studierId, List<Demand> demands) {
        this.studierId = studierId;
        this.demands = demands;
    }

    @Builder(builderMethodName = "newDemandsBuilder", builderClassName = "NewDemandsBuilder")
    public Demands(Id studierId, List<Demand> demands, SupplyAndDemandQuota supplyAndDemandQuota) {
        this.studierId = studierId;
        this.demands = demands;
        if (countActiveDemands() > supplyAndDemandQuota.getMaxActiveDemand())
            throw new InvalidInputException("Active Supplies exceed max active quota: " + supplyAndDemandQuota.getMaxActiveSupply());
        validate();
    }

    public Demand getDemandByAllowedSupplyId(Id allowedSupplyId) {
        for (Demand demand : demands)
            if (demand.getAllowedSupplyId().equals(allowedSupplyId))
                return demand;
        return null;
    }

    public static List<Id> extractStudierIdsOf(List<Demands> demandsList) {
        return demandsList.stream()
                .map(Demands::getStudierId)
                .collect(Collectors.toList());
    }

    public List<Id> getAllowedSupplyIds() {
        return demands.stream()
                .map(Demand::getAllowedSupplyId)
                .collect(Collectors.toList());
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

    public List<Demand> getDemandsSortedByPriority() {
        final List<Demand> result = new ArrayList<>(demands);
        result.sort((first, second) -> {
            return DemandPriority.compare(first.getPriority(), second.getPriority());
        });
        return result;
    }
}
