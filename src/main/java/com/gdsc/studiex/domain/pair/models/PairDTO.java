package com.gdsc.studiex.domain.pair.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PairDTO {
    private Id firstStudierId;
    private Id secondStudierId;
    private DateTime createdAt;
}
