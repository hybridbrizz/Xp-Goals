package com.ericversteeg.goal;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class GoalData {
    @SerializedName("last_check")
    public long lastCheck = 0L;

    @SerializedName("goals")
    public List<Goal> goals = new LinkedList<>();
}
