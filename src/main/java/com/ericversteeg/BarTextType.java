package com.ericversteeg;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BarTextType
{
    NONE,
    PERCENTAGE,
    PRECISE_PERCENTAGE,
    FRACTION,
    GAINED,
    REMAINING
}
