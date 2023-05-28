package com.ericversteeg.pattern;

import com.ericversteeg.scope.*;

import java.util.*;

public class Pattern {

    private Scope[] roots;
    private Scope [] scopes;

    public Pattern(Scope [] roots, Scope [] scopes)
    {
        this.roots = roots;
        this.scopes = scopes;
    }

    public boolean matches()
    {
        for (Scope root: roots)
        {
            if (stepMatch(root))
            {
                return true;
            }
        }
        return false;
    }

    private boolean stepMatch(Scope scope)
    {
        if (scope.getChildren().size() == 0)
        {
            return scope.matches(false);
        }

        for (Scope child: scope.getChildren())
        {
            if (stepMatch(child))
            {
                return scope.matches(false);
            }
        }
        return false;
    }

    public static Pattern parse(String pattern, Date estDate)
    {
        String removedRepeatsPattern = removeRepeatScopes(pattern);
        if (!removedRepeatsPattern.equals(pattern.trim()))
        {
            return parse(removedRepeatsPattern, estDate);
        }

        List<Scope> roots = new LinkedList<>();
        List<Scope> allScopes = new LinkedList<>();

        String [] scopeStrings = pattern.trim().split("->");

        List<Scope> parentScopes = new LinkedList<>();

        StringBuilder rebuiltPrefix = new StringBuilder();
        for (int i = 0; i < scopeStrings.length; i++)
        {
            String scopeString = scopeStrings[i];

            List<Scope> scopes = new LinkedList<>();

            if (scopeString.contains("^"))
            {
                if (i == 0)
                {
                    String fixedFormat = specificFixedFormat(scopeString);
                    if (!fixedFormat.isEmpty())
                    {
                        return parse(fixedFormat + rebuiltSuffix(scopeStrings, i + 1), estDate);
                    }
                }

                String [] fixedParts = scopeString.trim()
                        .replace("[", "")
                        .replace("]", "")
                        .split("\\^");

                String type = fixedParts[0]
                        .replace("(", "")
                        .replace(")", "")
                        .trim();

                if (fixedParts.length > 1)
                {
                    String fixedValueString = fixedParts[1]
                            .trim()
                            .replace("(", "")
                            .replace(")", "");

                    String [] fixedValueParts;
                    if (fixedValueString.contains("-"))
                    {
                        String [] rangeParts = fixedValueString.split("-");
                        String low = rangeParts[0].trim();
                        String high = rangeParts[1].trim();
                        fixedValueParts = numberRangeValues(low, high);
                    }
                    else
                    {
                        fixedValueParts = fixedValueString.split(",");
                    }

                    for (String fixedValuePart: fixedValueParts)
                    {
                        int fixedValue = Integer.parseInt(fixedValuePart.trim());
                        if (parentScopes.isEmpty())
                        {
                            scopes.add(fixedScope(null, type, fixedValue));
                            roots.add(scopes.get(scopes.size() - 1));
                        }
                        else
                        {
                            for (Scope parent: parentScopes)
                            {
                                scopes.add(fixedScope(parent, type, fixedValue));
                            }
                        }
                    }
                }
            }
            else
            {
                String [] scopeParts = scopeString.trim()
                        .replace("[", "")
                        .replace("]", "")
                        .split("\\|");

                int [] extras = getRepeatExtras(scopeParts);

                int offset = extras[0];
                int interval = extras[1];

                String type = scopeParts[0].trim()
                        .replace("(", "")
                        .replace(")", "");

                if (type.matches(".*[0-9]{4}"))
                {
                    String [] typeValues;
                    if (type.contains("-"))
                    {
                        String [] rangeParts = type.split("-");
                        String low = rangeParts[0].trim();
                        String high = rangeParts[1].trim();
                        typeValues = numberRangeValues(low, high);
                    }
                    else
                    {
                        typeValues = type.split(",");
                    }
                    return parse(specificYearsFormat(typeValues)
                            + rebuiltSuffix(scopeStrings, i + 1), estDate);
                }
                if (monthNumber(type) > 0)
                {
                    String [] typeValues;
                    if (type.contains("-"))
                    {
                        String [] rangeParts = type.split("-");
                        String low = rangeParts[0].trim();
                        String high = rangeParts[1].trim();
                        typeValues = monthRangeValues(low, high);
                    }
                    else
                    {
                        typeValues = type.split(",");
                    }
                    return parse(rebuiltPrefix + specificMonthsFormat(
                            typeValues, i, offset, interval)
                                    + rebuiltSuffix(scopeStrings, i + 1), estDate);
                }
                else if (dayOfWeekNumber(type) > 0)
                {
                    String[] typeValues;
                    if (type.contains("-")) {
                        String[] rangeParts = type.split("-");
                        String low = rangeParts[0].trim();
                        String high = rangeParts[1].trim();
                        typeValues = dayOfWeekRangeValues(low, high);
                    } else {
                        typeValues = type.split(",");
                    }
                    return parse(rebuiltPrefix + specificDaysOfWeekFormat(
                            typeValues, offset, interval)
                                    + rebuiltSuffix(scopeStrings, i + 1), estDate);
                }
                else if (hourOfDayNumber(type) > 0)
                {
                    String[] typeValues;
                    if (type.contains("-")) {
                        String[] rangeParts = type.split("-");
                        String low = rangeParts[0].trim();
                        String high = rangeParts[1].trim();
                        typeValues = hourOfDayRangeValues(low, high);
                    } else {
                        typeValues = type.split(",");
                    }
                    return parse(rebuiltPrefix + specificHoursFormat(
                            typeValues, offset, interval)
                            + rebuiltSuffix(scopeStrings, i + 1), estDate);
                }

                if (parentScopes.isEmpty())
                {
                    scopes.add(repeatScope(null, estDate, type, offset, interval));
                    roots.add(scopes.get(scopes.size() - 1));
                }
                else
                {
                    for (Scope parent: parentScopes)
                    {
                        scopes.add(repeatScope(parent, estDate, type, offset, interval));
                    }
                }
            }

            allScopes.addAll(scopes);

            parentScopes.clear();
            parentScopes.addAll(scopes);

            rebuiltPrefix.append(scopeString).append("->");
        }

        return new Pattern(roots.toArray(new Scope[0]), allScopes.toArray(new Scope[0]));
    }

    private static String removeRepeatScopes(String pattern)
    {
        String [] scopeStrings = pattern.trim().split("->");

        StringBuilder rebuiltPattern = new StringBuilder();
        Set<String> scopeTypes = new HashSet<>();
        for (int i = 0; i < scopeStrings.length; i++) {
            String scopeString = scopeStrings[i]
                    .replace("[", "")
                    .replace("]", "")
                    .replace("(", "")
                    .replace(")", "");

            if (scopeString.contains("^"))
            {
                String type = scopeString.split("\\^")[0].trim().toLowerCase();
                if (!scopeTypes.contains(type))
                {
                    scopeTypes.add(type);
                    rebuiltPattern.append(scopeStrings[i]);
                    rebuiltPattern.append("->");
                }
            }
            else
            {
                String type = scopeString.split("\\|")[0].trim().toLowerCase();
                if (!scopeTypes.contains(type))
                {
                    scopeTypes.add(type);
                    rebuiltPattern.append(scopeStrings[i]);
                    rebuiltPattern.append("->");
                }
            }
        }
        return rebuiltPattern.substring(0, rebuiltPattern.length() - 2);
    }

    private static String rebuiltSuffix(String [] scopeStrings, int fromIndex)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = fromIndex; i < scopeStrings.length; i++)
        {
            sb.append("->");
            sb.append(scopeStrings[i]);
        }
        return sb.toString();
    }

    private static Scope fixedScope(Scope parent, String type, int value)
    {
        switch (type.toLowerCase())
        {
            case "y":
                return new YearScope(parent, value);
            case "m":
                return new MonthScope(parent, value);
            case "w":
                return new WeekScope(parent, value);
            case "d":
                return new DayScope(parent, value);
            case "h":
                return new HourScope(parent, value);
        }
        return null;
    }

    private static Scope repeatScope(Scope parent, Date estDate, String type, int offset, int interval)
    {
        switch (type.toLowerCase())
        {
            case "y":
                return new YearScope(estDate, offset, interval);
            case "m":
                if (parent != null)
                {
                    return new MonthScope(parent, offset, interval);
                }
                else
                {
                    return new MonthScope(estDate, offset, interval);
                }
            case "w":
                if (parent != null)
                {
                    return new WeekScope(parent, offset, interval);
                }
                else
                {
                    return new WeekScope(estDate, offset, interval);
                }
            case "d":
                if (parent != null)
                {
                    return new DayScope(parent, offset, interval);
                }
                else
                {
                    return new DayScope(estDate, offset, interval);
                }
            case "h":
                if (parent != null)
                {
                    return new HourScope(parent, offset, interval);
                }
                else
                {
                    return new HourScope(estDate, offset, interval);
                }
        }
        return null;
    }

    private static int [] getRepeatExtras(String [] scopeParts)
    {
        int offset = 0;
        int interval = 1;

        String [] extraParts = new String[0];
        if (scopeParts.length > 1)
        {
            extraParts = new String [] { scopeParts[1].trim() };
        }

        if (scopeParts.length > 2)
        {
            extraParts = new String [] { scopeParts[1].trim(), scopeParts[2].trim() };
        }

        for (String part: extraParts)
        {
            if (part.startsWith("~")) { offset = Integer.parseInt(part.substring(1)); }
            else { interval = Integer.parseInt(part); }
        }

        return new int [] {offset, interval};
    }

    private static String specificFixedFormat(String scopeString)
    {
        if (scopeString.toLowerCase().contains("m"))
        {
            return "[Y]->" + scopeString;
        }
        else if (scopeString.toLowerCase().contains("w") ||
                 scopeString.toLowerCase().contains("d"))
        {
            return "[M]->" + scopeString;
        }
        return "";
    }

    private static String specificYearsFormat(String [] years)
    {
        StringBuilder yearsCsv = new StringBuilder();
        for (String year: years)
        {
            yearsCsv.append(year).append(",");
        }
        return "[Y^" + yearsCsv.substring(0, yearsCsv.length() - 1) + "]";
    }

    private static String specificMonthsFormat(String [] months, int scopeIndex, int offset, int interval)
    {
        StringBuilder monthCsv = new StringBuilder();
        for (String month: months)
        {
            monthCsv.append(monthNumber(month)).append(",");
        }
        String format = "";
        if (scopeIndex == 0)
        {
            format += "[Y|" + "~" + offset + "|" + interval + "]->";
        }
        format += "[M^" + monthCsv.substring(0, monthCsv.length() - 1) + "]";
        return format;
    }

    private static int monthNumber(String month)
    {
        String lcMonth = month.toLowerCase().trim();
        if (lcMonth.startsWith("jan")) { return 1; }
        else if (lcMonth.startsWith("feb")) { return 2; }
        else if (lcMonth.startsWith("mar")) { return 3; }
        else if (lcMonth.startsWith("apr")) { return 4; }
        else if (lcMonth.startsWith("may")) { return 5; }
        else if (lcMonth.startsWith("jun")) { return 6; }
        else if (lcMonth.startsWith("jul")) { return 7; }
        else if (lcMonth.startsWith("aug")) { return 8; }
        else if (lcMonth.startsWith("sep")) { return 9; }
        else if (lcMonth.startsWith("oct")) { return 10; }
        else if (lcMonth.startsWith("nov")) { return 11; }
        else if (lcMonth.startsWith("dec")) { return 12; }
        return -1;
    }

    private static String month(int number)
    {
        if (number == 1) { return "jan"; }
        else if (number == 2) { return "feb"; }
        else if (number == 3) { return "mar"; }
        else if (number == 4) { return "apr"; }
        else if (number == 5) { return "may"; }
        else if (number == 6) { return "jun"; }
        else if (number == 7) { return "jul"; }
        else if (number == 8) { return "aug"; }
        else if (number == 9) { return "sep"; }
        else if (number == 10) { return "oct"; }
        else if (number == 11) { return "nov"; }
        else if (number == 12) { return "dec"; }
        return "";
    }

    private static String specificDaysOfWeekFormat(String [] daysOfWeek, int offset, int interval)
    {
        StringBuilder daysOfWeekCsv = new StringBuilder();
        for (String day: daysOfWeek)
        {
            daysOfWeekCsv.append(dayOfWeekNumber(day)).append(",");
        }
        return "[W|" + "~" + offset + "|" + interval + "]->[D^" + daysOfWeekCsv.substring(0, daysOfWeekCsv.length() - 1) + "]";
    }

    private static int dayOfWeekNumber(String dayOfWeek)
    {
        String lcDayOfWeek = dayOfWeek.toLowerCase().trim();
        if (lcDayOfWeek.startsWith("mo")) { return 1; }
        else if (lcDayOfWeek.startsWith("tu")) { return 2; }
        else if (lcDayOfWeek.startsWith("we")) { return 3; }
        else if (lcDayOfWeek.startsWith("th")) { return 4; }
        else if (lcDayOfWeek.startsWith("fr")) { return 5; }
        else if (lcDayOfWeek.startsWith("sa")) { return 6; }
        else if (lcDayOfWeek.startsWith("su")) { return 7; }
        return -1;
    }

    private static String dayOfWeek(int number)
    {
        if (number == 1) { return "mo"; }
        else if (number == 2) { return "tu"; }
        else if (number == 3) { return "we"; }
        else if (number == 4) { return "th"; }
        else if (number == 5) { return "fr"; }
        else if (number == 6) { return "sa"; }
        else if (number == 7) { return "su"; }
        return "";
    }

    private static String specificHoursFormat(String [] hoursOfDay, int offset, int interval)
    {
        StringBuilder hoursOfDayCsv = new StringBuilder();
        for (String hour: hoursOfDay)
        {
            hoursOfDayCsv.append(hourOfDayNumber(hour)).append(",");
        }
        return "[W|" + "~" + offset + "|" + interval + "]->[H^" + hoursOfDayCsv.substring(0, hoursOfDayCsv.length() - 1) + "]";
    }

    private static int hourOfDayNumber(String dayOfWeek)
    {
        String lcHour = dayOfWeek.toLowerCase().trim().replace(" ", "");
        if (lcHour.startsWith("12am")) { return 0; }
        else if (lcHour.startsWith("1am")) { return 1; }
        else if (lcHour.startsWith("2am")) { return 2; }
        else if (lcHour.startsWith("3am")) { return 3; }
        else if (lcHour.startsWith("4am")) { return 4; }
        else if (lcHour.startsWith("5am")) { return 5; }
        else if (lcHour.startsWith("6am")) { return 6; }
        else if (lcHour.startsWith("7am")) { return 7; }
        else if (lcHour.startsWith("8am")) { return 8; }
        else if (lcHour.startsWith("9am")) { return 9; }
        else if (lcHour.startsWith("10am")) { return 10; }
        else if (lcHour.startsWith("11am")) { return 11; }
        else if (lcHour.startsWith("12pm")) { return 12; }
        else if (lcHour.startsWith("1pm")) { return 13; }
        else if (lcHour.startsWith("2pm")) { return 14; }
        else if (lcHour.startsWith("3pm")) { return 15; }
        else if (lcHour.startsWith("4pm")) { return 16; }
        else if (lcHour.startsWith("5pm")) { return 17; }
        else if (lcHour.startsWith("6pm")) { return 18; }
        else if (lcHour.startsWith("7pm")) { return 19; }
        else if (lcHour.startsWith("8pm")) { return 20; }
        else if (lcHour.startsWith("9pm")) { return 21; }
        else if (lcHour.startsWith("10pm")) { return 22; }
        else if (lcHour.startsWith("11pm")) { return 23; }
        return -1;
    }

    private static String hourOfDay(int number)
    {
        if (number == 0) { return "12am"; }
        else if (number == 1) { return "1am"; }
        else if (number == 2) { return "2am"; }
        else if (number == 3) { return "3am"; }
        else if (number == 4) { return "4am"; }
        else if (number == 5) { return "5am"; }
        else if (number == 6) { return "6am"; }
        else if (number == 7) { return "7am"; }
        else if (number == 8) { return "8am"; }
        else if (number == 9) { return "9am"; }
        else if (number == 10) { return "10am"; }
        else if (number == 11) { return "11am"; }
        else if (number == 12) { return "12pm"; }
        else if (number == 13) { return "1pm"; }
        else if (number == 14) { return "2pm"; }
        else if (number == 15) { return "3pm"; }
        else if (number == 16) { return "4pm"; }
        else if (number == 17) { return "5pm"; }
        else if (number == 18) { return "6pm"; }
        else if (number == 19) { return "7pm"; }
        else if (number == 20) { return "8pm"; }
        else if (number == 21) { return "9pm"; }
        else if (number == 22) { return "10pm"; }
        else if (number == 23) { return "11pm"; }
        return "";
    }

    private static String [] numberRangeValues(String low, String high)
    {
        int lowNum = Integer.parseInt(low);
        int highNum = Integer.parseInt(high);

        if (highNum < lowNum)
        {
            int temp = highNum;
            highNum = lowNum;
            lowNum = temp;
        }

        String [] values = new String [highNum - lowNum + 1];
        for (int i = lowNum; i <= highNum; i++)
        {
            values[i - lowNum] = String.valueOf(i);
        }
        return values;
    }

    private static String [] monthRangeValues(String low, String high)
    {
        int lowNum = monthNumber(low);
        int highNum = monthNumber(high);

        if (highNum < lowNum)
        {
            int temp = highNum;
            highNum = lowNum;
            lowNum = temp;
        }

        String [] values = new String [highNum - lowNum + 1];
        for (int i = lowNum; i <= highNum; i++)
        {
            values[i - lowNum] = month(i);
        }
        return values;
    }

    private static String [] dayOfWeekRangeValues(String low, String high)
    {
        int lowNum = dayOfWeekNumber(low);
        int highNum = dayOfWeekNumber(high);

        if (highNum < lowNum)
        {
            int temp = highNum;
            highNum = lowNum;
            lowNum = temp;
        }

        String [] values = new String [highNum - lowNum + 1];
        for (int i = lowNum; i <= highNum; i++)
        {
            values[i - lowNum] = dayOfWeek(i);
        }
        return values;
    }

    private static String [] hourOfDayRangeValues(String low, String high)
    {
        int lowNum = hourOfDayNumber(low);
        int highNum = hourOfDayNumber(high);

        if (highNum == 0)
        {
            highNum = 24;
        }

        if (highNum < lowNum)
        {
            int temp = highNum;
            highNum = lowNum;
            lowNum = temp;
        }
        else if (highNum == lowNum)
        {
            return new String [] { hourOfDay(highNum) };
        }

        highNum -= 1;

        String [] values = new String [highNum - lowNum + 1];
        for (int i = lowNum; i <= highNum; i++)
        {
            values[i - lowNum] = hourOfDay(i);
        }
        return values;
    }
}
