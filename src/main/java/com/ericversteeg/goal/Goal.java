package com.ericversteeg.goal;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class Goal {
    public static final int resetHourly = 0;
    public static final int resetDaily = 1;
    public static final int resetWeekly = 2;
    public static final int resetMonthly = 3;
    public static final int resetYearly = 4;
    public static final int resetNone = 5;

    public Goal(int skillId)
    {
        this.skillId = skillId;
    }

    @SerializedName("skill_id")
    public int skillId = 0;

    @SerializedName("reset_type")
    public int resetType = resetDaily;

    @SerializedName("progress_xp")
    public int progressXp = -1;

    @SerializedName("past_progress")
    public List<Float> pastProgress = new LinkedList<>();

    public int goalXp = 0;

    public boolean enabled = false;
    public boolean track = false;

    public void reset()
    {
        if (progressXp < 0) return;

        if (pastProgress == null)
        {
            pastProgress = new LinkedList<>();
        }

        pastProgress.add(0,
            (progressXp) /
            ((float) goalXp)
        );

        List<Float> recentPastProgress = new LinkedList<>();
        for (int i = 0; i < Math.min(pastProgress.size(), 25); i++)
        {
            recentPastProgress.add(pastProgress.get(i));
        }

        pastProgress = recentPastProgress;

        System.out.println("Reset goal skill id = " + skillId);

        progressXp = -1;
    }
}
