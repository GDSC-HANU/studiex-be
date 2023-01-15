package com.gdsc.studiex.domain.share.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Pair<F,S>{
    private F first;
    private S second;
}
