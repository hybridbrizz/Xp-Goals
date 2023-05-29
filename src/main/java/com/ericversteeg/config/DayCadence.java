package com.ericversteeg.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayCadence
{
    NONE(""),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11"),
    TWELVE("12"),
    THIRTEEN("13"),
    FOURTEEN("14"),
    FIFTEEN("15"),
    SIXTEEN("16"),
    SEVENTEEN("17"),
    EIGHTEEN("18"),
    NINETEEN("19"),
    TWENTY("20"),
    TWENTY_ONE("21"),
    TWENTY_TWO("22"),
    TWENTY_THREE("23");

    private final String name;
}
