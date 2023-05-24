package com.ericversteeg.scope;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class Scope
{
    protected LocalDate estLocalDate;
    protected LocalDateTime refLocalDate;
    protected LocalDate nowLocalDate = LocalDate.now();

    public enum Type
    {
        FIXED,
        REPEAT
    }

    protected Scope parent = null;

    private List<Scope> children = new LinkedList<>();

    private Type type;

    private Date estDate;

    // fixed
    protected TemporalField temporalField;
    protected int value;

    // repeat
    private int offset;
    private int interval;

    public Scope()
    {

    }

    public Scope(Scope parent)
    {
        this.parent = parent;
        if (parent != null)
        {
            parent.addChild(this);
        }
    }

    public Scope(Scope parent, int value)
    {
        this.parent = parent;
        if (parent != null)
        {
            parent.addChild(this);
        }

        this.type = Type.FIXED;

        this.value = value;
    }

    public Scope(Scope parent, TemporalField temporalField, int value)
    {
        this.parent = parent;
        if (parent != null)
        {
            parent.addChild(this);
        }

        this.type = Type.FIXED;

        this.temporalField = temporalField;
        this.value = value;

        setReferenceDate();
    }

    public Scope(Scope parent, int offset, int interval)
    {
        this.parent = parent;
        if (parent != null)
        {
            parent.addChild(this);
        }

        this.type = Type.REPEAT;

        this.offset = offset;
        this.interval = interval;

        setReferenceDate();
    }

    public Scope(Date estDate, int offset, int interval)
    {
        this.type = Type.REPEAT;

        estLocalDate = estDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        this.offset = offset;
        this.interval = interval;

        setReferenceDate();
    }

    void setReferenceDate()
    {
        if (parent == null)
        {
            if (this instanceof  HourScope)
            {
                refLocalDate = estLocalDate.atTime(estLocalDate.get(ChronoField.HOUR_OF_DAY), 0);
            }
            else if (this instanceof DayScope)
            {
                refLocalDate = estLocalDate.atStartOfDay();
            }
            else if (this instanceof WeekScope)
            {
                refLocalDate = estLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
            }
            else if (this instanceof MonthScope)
            {
                refLocalDate = estLocalDate.withDayOfMonth(1).atStartOfDay();
            }
            else if (this instanceof YearScope)
            {
                refLocalDate = estLocalDate.withDayOfYear(1).atStartOfDay();
            }
        }
        else if (parent instanceof YearScope)
        {
            refLocalDate = nowLocalDate.withDayOfYear(1).atStartOfDay();
        }
        else if (parent instanceof MonthScope)
        {
            refLocalDate = nowLocalDate.withDayOfMonth(1).atStartOfDay();
        }
        else if (parent instanceof WeekScope)
        {
            refLocalDate = nowLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        }
        else if (parent instanceof DayScope)
        {
            refLocalDate = nowLocalDate.atStartOfDay();
        }
    }

    public boolean matches(boolean alsoParents)
    {
        if (type == null)
        {
            if (parent != null && alsoParents)
            {
                return parent.matches(true);
            }
            else
            {
                return true;
            }
        }

        if (type == Type.FIXED)
        {
            if (parent != null && alsoParents)
            {
                return parent.matches(true) && matchesFixed();
            }
            else
            {
                return matchesFixed();
            }
        }

        if (parent != null && alsoParents)
        {
            return parent.matches(true) && matchesInterval(refLocalDate, nowLocalDate, offset, interval);
        }
        else
        {
            return matchesInterval(refLocalDate, nowLocalDate, offset, interval);
        }
    }

    protected boolean matchesFixed()
    {
        if (this instanceof HourScope)
        {
            return nowLocalDate.atTime(LocalTime.now()).get(temporalField) == value;
        }
        else
        {
            return nowLocalDate.get(temporalField) == value;
        }
    }

    abstract boolean matchesInterval(LocalDateTime estLocalDate, LocalDate nowLocalDate, int offset, int interval);

    public void addChild(Scope scope)
    {
        children.add(scope);
    }

    public List<Scope> getChildren()
    {
        return children;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "estLocalDate=" + estLocalDate +
                ", refLocalDate=" + refLocalDate +
                ", nowLocalDate=" + nowLocalDate +
                ", parent=" + parent +
                ", type=" + type +
                ", estDate=" + estDate +
                ", temporalField=" + temporalField +
                ", value=" + value +
                ", offset=" + offset +
                ", interval=" + interval +
                '}';
    }
}
