package com.gdsc.studyex.domain.supply_and_demand.models.supplies_quota;

import com.gdsc.studyex.domain.share.models.DateTime;
import lombok.Getter;

@Getter
public class SuppliesQuota {
    private int minQuota;
    private int maxQuota;
    private DateTime updatedAt;
}
