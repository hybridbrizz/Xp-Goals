package com.ericversteeg;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class Goal {
    static final int resetHourly = 0;
    static final int resetDaily = 1;
    static final int resetWeekly = 2;
    static final int resetMonthly = 3;
    static final int resetYearly = 4;
    static final int resetNone = 5;

    public Goal(int skillId)
    {
        this.skillId = skillId;
    }

    @SerializedName("skill_id")
    int skillId = 0;

    @SerializedName("reset_type")
    int resetType = resetDaily;

    @SerializedName("progress_xp")
    int progressXp = -1;

    @SerializedName("past_progress")
    List<Float> pastProgress = new LinkedList<>();

    int goalXp = 0;

    boolean enabled = false;
    boolean track = false;

    void reset()
    {
        if (progressXp < 0) return;

        if (pastProgress == null)
        {
            pastProgress = new LinkedList<>();
        }

        pastProgress.add(
                (progressXp) /
                ((float) goalXp)
        );

        System.out.println("Reset goal skill id = " + skillId);

        progressXp = -1;
    }
}
