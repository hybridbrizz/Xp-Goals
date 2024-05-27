package com.ericversteeg.scope;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class WeekScope extends Scope
{
    public WeekScope(Scope parent, int value, long offsetBy)
    {
        super(parent, value, offsetBy);

        if (parent instanceof YearScope)
        {
            temporalField = IsoFields.WEEK_OF_WEEK_BASED_YEAR;
        }
    }

    public WeekScope(Scope parent, int offset, int interval, long offsetBy)
    {
        super(parent, offset, interval, offsetBy);
    }

    public WeekScope(Date estDate, int offset, int interval, long offsetBy)
    {
        super(estDate, offset, interval, offsetBy);
    }

    @Override
    protected boolean matchesFixed()
    {
        if (parent instanceof MonthScope)
        {
            return (nowLocalDate.get(ChronoField.DAY_OF_MONTH) - 1) / 7 + 1 == value;
        }
        else
        {
            return super.matchesFixed();
        }
    }

    @Override
    boolean matchesInterval(LocalDateTime refLocalDate, LocalDate nowLocalDate, int offset, int interval)
    {
        LocalDateTime nowSoW = nowLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();

        long weeksSince = ChronoUnit.WEEKS.between(refLocalDate, nowSoW);

        return (weeksSince - offset) >= 0 && (weeksSince - offset) % interval == 0;
    }
}
