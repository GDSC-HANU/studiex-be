package com.gdsc.studiex.domain.supply_and_demand.models.supplies_quota;

import com.gdsc.studiex.domain.share.models.DateTime;
import lombok.Getter;

@Getter
public class SuppliesQuota {
    private int maxActiveQuota;
    private DateTime updatedAt;
}
