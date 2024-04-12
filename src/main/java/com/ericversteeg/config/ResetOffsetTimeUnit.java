package com.ericversteeg.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResetOffsetTimeUnit
{
    MINUTE("Minute"),
    HOUR("Hour"),
    DAY("Day");

    private final String name;
}
