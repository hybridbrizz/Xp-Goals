package com.ericversteeg.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType
{
    PERCENTAGE,
    PERCENTAGE_FLIP,
    XP_GAINED,
    XP_GAINED_FLIP,
    XP_REMAINING,
    XP_REMAINING_FLIP
}
