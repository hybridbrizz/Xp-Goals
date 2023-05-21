package com.ericversteeg.pattern;

import com.ericversteeg.scope.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Pattern {

    private Scope root;
    private Scope[] scopes;

    public Pattern(Scope root, Scope[] scopes)
    {
        this.root = root;
        this.scopes = scopes;
    }

    public boolean matches()
    {
        return stepMatch(root);
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
        Scope root = null;
        List<Scope> allScopes = new LinkedList<>();

        String [] scopeStrings = pattern.trim().split("->");

        List<Scope> parentScopes = new LinkedList<>();
        for (String scopeString: scopeStrings)
        {
            List<Scope> scopes = new LinkedList<>();

            if (scopeString.contains("^"))
            {
                String [] fixedParts = scopeString.trim()
                        .replace("[", "")
                        .replace("]", "")
                        .split("\\^");

                String type = fixedParts[0].trim();

                if (fixedParts.length > 1)
                {
                    String [] fixedValueStrings = fixedParts[1].trim().split(",");
                    for (String fixedValueString: fixedValueStrings)
                    {
                        int fixedValue = Integer.parseInt(fixedValueString.trim());
                        if (parentScopes.isEmpty())
                        {
                            scopes.add(fixedScope(null, type, fixedValue));
                            if (root == null)
                            {
                                root = scopes.get(0);
                            }
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

                String type = scopeParts[0].trim();
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

                if (parentScopes.isEmpty())
                {
                    scopes.add(repeatScope(null, estDate, type, offset, interval));
                    if (root == null)
                    {
                        root = scopes.get(0);
                    }
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
        }

        return new Pattern(root, allScopes.toArray(new Scope[0]));
    }

    private static Scope fixedScope(Scope parent, String type, int value)
    {
        switch (type)
        {
            case "Y":
                return new YearScope(parent, value);
            case "M":
                return new MonthScope(parent, value);
            case "W":
                return new WeekScope(parent, value);
            case "D":
                return new DayScope(parent, value);
        }
        return null;
    }

    private static Scope repeatScope(Scope parent, Date estDate, String type, int offset, int interval)
    {
        switch (type)
        {
            case "Y":
                return new YearScope(estDate, offset, interval);
            case "M":
                if (parent != null)
                {
                    return new MonthScope(parent, offset, interval);
                }
                else
                {
                    return new MonthScope(estDate, offset, interval);
                }
            case "W":
                if (parent != null)
                {
                    return new WeekScope(parent, offset, interval);
                }
                else
                {
                    return new WeekScope(estDate, offset, interval);
                }
            case "D":
                if (parent != null)
                {
                    return new DayScope(parent, offset, interval);
                }
                else
                {
                    return new DayScope(estDate, offset, interval);
                }
        }
        return null;
    }
}
