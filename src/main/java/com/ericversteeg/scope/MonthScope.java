package com.ericversteeg.scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MonthScope extends Scope
{
    public MonthScope()
    {
        super();
    }

    public MonthScope(Scope parent)
    {
        super(parent);
    }

    public MonthScope(Scope parent, int value)
    {
        super(parent, value);

        if (parent instanceof YearScope)
        {
            temporalField = ChronoField.MONTH_OF_YEAR;
        }
    }

    public MonthScope(Scope parent, int offset, int interval)
    {
        super(parent, offset, interval);
    }

    public MonthScope(Date estDate, int offset, int interval)
    {
        super(estDate, offset, interval);
    }

    @Override
    boolean matchesInterval(LocalDateTime refLocalDate, LocalDate nowLocalDate, int offset, int interval)
    {
        LocalDateTime nowSoM = nowLocalDate.withDayOfMonth(1).atStartOfDay();

        long monthsSince = ChronoUnit.MONTHS.between(refLocalDate, nowSoM);

        return (monthsSince - offset) >= 0 && (monthsSince - offset) % interval == 0;
    }
}
