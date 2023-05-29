package com.ericversteeg.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Hour
{
    NONE(""),
    MIDNIGHT("12AM"),
    ONE_AM("1AM"),
    TWO_AM("2AM"),
    THREE_AM("3AM"),
    FOUR_AM("4AM"),
    FIVE_AM("5AM"),
    SIX_AM("6AM"),
    SEVEN_AM("7AM"),
    EIGHT_AM("8AM"),
    NINE_AM("9AM"),
    TEN_AM("10AM"),
    ELEVEN_AM("11AM"),
    NOON("12AM"),
    ONE_PM("1PM"),
    TWO_PM("2PM"),
    THREE_PM("3PM"),
    FOUR_PM("4PM"),
    FIVE_PM("5PM"),
    SIX_PM("6PM"),
    SEVEN_PM("7PM"),
    EIGHT_PM("8PM"),
    NINE_PM("9PM"),
    TEN_PM("10PM"),
    ELEVEN_PM("11PM");

    private final String name;
}
