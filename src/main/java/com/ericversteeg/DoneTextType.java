package com.ericversteeg;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DoneTextType
{
    NONE(""),
    DONE("Done"),
    COMPLETE("Complete"),
    COMPLETED("Completed"),
    FINISHED("Finished");

    private final String text;
}
