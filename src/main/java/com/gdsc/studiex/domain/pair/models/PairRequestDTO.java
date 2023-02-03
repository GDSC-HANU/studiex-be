package com.gdsc.studiex.domain.pair.models;

import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PairRequestDTO {
    public Id fromStudierId;
    public Id toStudierId;
    public DateTime createdAt;
}
