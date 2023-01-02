package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;

public interface AllowedSupplyItemValue {
    public SupplyItemValue toSupplyItemValue(AllowedSupplyOperator allowedSupplyOperator,
                                             SupplyItemOperator supplyItemOperator,
                                             SuppliesDTO.SupplyItemValueDTO supplyItemValueDTO) throws InvalidInputException;

    public DemandItemValue toDemandItemValue(AllowedSupplyOperator allowedSupplyOperator,
                                             DemandItemOperator demandItemOperator,
                                             DemandsDTO.DemandItemValueDTO demandItemValueDTO) throws InvalidInputException;

    public AllowedSupplyDTO.AllowedSupplyItemValueDTO toAllowedSupplyItemValueDTO();

    public boolean canBeUsedWith(AllowedSupplyOperator operator);
}
