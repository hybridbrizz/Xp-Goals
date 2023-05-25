package com.ericversteeg;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class GoalData {
    @SerializedName("last_reset")
    long lastReset = 0L;

    @SerializedName("last_check")
    long lastCheck = 0L;

    @SerializedName("goals")
    List<Goal> goals = new LinkedList<>();
}
