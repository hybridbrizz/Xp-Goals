package com.ericversteeg.scope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class YearScope extends Scope {

    public YearScope(int value, long offsetBy)
    {
        super(null, value, offsetBy);

        temporalField = ChronoField.YEAR;
    }

    public YearScope(Scope parent, int value, long offsetBy)
    {
        super(parent, value, offsetBy);

        temporalField = ChronoField.YEAR;
    }

    public YearScope(Date estDate, int offset, int interval, long offsetBy)
    {
        super(estDate, offset, interval, offsetBy);
    }

    @Override
    boolean matchesInterval(LocalDateTime refLocalDate, LocalDate nowLocalDate, int offset, int interval)
    {
        LocalDateTime nowSoD = nowLocalDate.withDayOfYear(1).atStartOfDay();

        long daysSince = ChronoUnit.DAYS.between(refLocalDate, nowSoD);

        return (daysSince - offset) >= 0 && (daysSince - offset) % interval == 0;
    }
}
