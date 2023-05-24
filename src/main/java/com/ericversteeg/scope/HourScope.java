package com.ericversteeg.scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class HourScope extends Scope
{
    public HourScope(Scope parent)
    {
        super(parent);
    }

    public HourScope(Scope parent, int value)
    {
        super(parent, value);

        temporalField = ChronoField.HOUR_OF_DAY;
    }

    public HourScope(Scope parent, int offset, int interval)
    {
        super(parent, offset, interval);
    }

    public HourScope(Date estDate, int offset, int interval)
    {
        super(estDate, offset, interval);
    }

    @Override
    boolean matchesInterval(LocalDateTime refLocalDate, LocalDate nowLocalDate, int offset, int interval)
    {
        LocalDateTime nowSoH = nowLocalDate.atTime(nowLocalDate.get(ChronoField.HOUR_OF_DAY), 0);

        long hoursSince = ChronoUnit.HOURS.between(refLocalDate, nowSoH);

        return (hoursSince - offset) >= 0 && (hoursSince - offset) % interval == 0;
    }
}
