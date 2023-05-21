package com.ericversteeg.scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class YearScope extends Scope {

    public YearScope()
    {
        super();
    }

    public YearScope(int value)
    {
        super(null, value);

        temporalField = ChronoField.YEAR;
    }

    public YearScope(Scope parent, int value)
    {
        super(parent, value);

        temporalField = ChronoField.YEAR;
    }

    public YearScope(Date estDate, int offset, int interval)
    {
        super(estDate, offset, interval);
    }

    @Override
    boolean matchesInterval(LocalDateTime refLocalDate, LocalDate nowLocalDate, int offset, int interval)
    {
        LocalDateTime nowSoD = nowLocalDate.withDayOfYear(1).atStartOfDay();

        long daysSince = ChronoUnit.DAYS.between(refLocalDate, nowSoD);

        return (daysSince - offset) >= 0 && (daysSince - offset) % interval == 0;
    }
}
