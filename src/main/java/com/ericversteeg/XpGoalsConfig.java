package com.ericversteeg;

import com.ericversteeg.bar.*;
import com.ericversteeg.config.AnchorType;
import com.ericversteeg.config.DayCadence;
import com.ericversteeg.config.Hour;
import com.ericversteeg.goal.ResetType;
import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(XpGoalsConfig.GROUP)
public interface XpGoalsConfig extends Config
{
	String GROUP = "xpgoals_3";

	@ConfigItem(
			position = 0,
			keyName = "Text",
			name = "Label",
			description = "Configures label."
	)
	default String labelText() { return "Goals"; }

	@ConfigItem(
			position = 1,
			keyName = "anchorType",
			name = "Anchor",
			description = "Configures anchor for the panel."
	)
	default AnchorType anchorType() { return AnchorType.TOP_LEFT; }

	@ConfigItem(
			position = 1,
			keyName = "anchorX",
			name = "X Position",
			description = "Configures x position relative to the anchor."
	)
	default int anchorX() { return 10; }

	@ConfigItem(
			position = 2,
			keyName = "anchorY",
			name = "Y Position",
			description = "Configures y position relative to the anchor."
	)
	default int anchorY() { return 120; }

	@ConfigItem(
			position = 3,
			keyName = "barWidth",
			name = "Bar Width",
			description = "Configures width of bars."
	)
	default int barWidth() { return 115; }

	@ConfigItem(
			position = 4,
			keyName = "barHeight",
			name = "Bar Height",
			description = "Configures height of bars."
	)
	default int barHeight() { return 20; }

	@ConfigItem(
			position = 5,
			keyName = "barSpacing",
			name = "Bar Spacing",
			description = "Configures space between bars."
	)
	default int barSpacing() { return 5; }

	@ConfigItem(
			position = 6,
			keyName = "barTextType",
			name = "Value Text Type",
			description = "Configures what type of value is displayed."
	)
	default BarTextType barTextType() { return BarTextType.FRACTION; }

	@ConfigItem(
			position = 7,
			keyName = "barTextPosition",
			name = "Value Text Position",
			description = "Configures bar text position."
	)
	default BarTextPosition barTextPosition() { return BarTextPosition.INSIDE; }

	@ConfigItem(
			position = 7,
			keyName = "barTextSize",
			name = "Value Text Size",
			description = "Configures bar text size."
	)
	default BarTextSize barTextSize() { return BarTextSize.LARGE; }

	@ConfigItem(
			position = 7,
			keyName = "barTextAlignment",
			name = "Value Text Alignment",
			description = "Configures bar text alignment."
	)
	default BarTextAlignment barTextAlignment() { return BarTextAlignment.CENTER; }

	@ConfigItem(
			position = 8,
			keyName = "pastProgressSpan",
			name = "Tooltip Grid Size",
			description = "Configures grid size in the tooltip."
	)
	default int pastProgressSpan() { return 3; }

	@ConfigItem(
			position = 8,
			keyName = "doneTextType",
			name = "Completion Text",
			description = "Configures completion text for each gaol."
	)
	default DoneTextType doneTextType() { return DoneTextType.COMPLETE; }

	@ConfigItem(
			position = 9,
			keyName = "hideIcons",
			name = "Hide Icons",
			description = "Configures whether or not to hide skill icons."
	)
	default boolean hideSkillIcons() { return false; }

	@ConfigItem(
			position = 10,
			keyName = "includeResetType",
			name = "Include Reset Label",
			description = "Configures whether or not to include reset label in bar text."
	)
	default boolean includeResetType() { return false; }

	@ConfigItem(
			position = 11,
			keyName = "enableOverfill",
			name = "Enable Bar Overfill",
			description = "Configures whether or not bar overfills to 200%."
	)
	default boolean enableOverfill() { return true; }

	@ConfigItem(
			position = 12,
			keyName = "overfillColor",
			name = "Overfill Color",
			description = "Configures bar overfill color."
	)
	default Color overfillColor() { return Color.decode("#A020F0"); }

	@ConfigSection(
			name = "Attack",
			description = "Attack Skill",
			position = 20,
			closedByDefault = true
	)
	String attackSkill = "attackSkill";

	@ConfigItem(
			keyName = "enableAttack",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not attack is enabled.",
			section = attackSkill
	)
	default boolean enableAttackSkill() { return false; }

	@ConfigItem(
			keyName = "attackXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = attackSkill
	)
	default int attackXpGoal() { return 1; }

	@ConfigItem(
			keyName = "attackResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = attackSkill
	)
	default ResetType attackResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "attackMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = attackSkill
	)
	default boolean attackMonday() { return false; }

	@ConfigItem(
			keyName = "attackTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = attackSkill
	)
	default boolean attackTuesday() { return false; }

	@ConfigItem(
			keyName = "attackWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = attackSkill
	)
	default boolean attackWednesday() { return false; }

	@ConfigItem(
			keyName = "attackThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = attackSkill
	)
	default boolean attackThursday() { return false; }

	@ConfigItem(
			keyName = "attackFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = attackSkill
	)
	default boolean attackFriday() { return false; }

	@ConfigItem(
			keyName = "attackSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = attackSkill
	)
	default boolean attackSaturday() { return false; }

	@ConfigItem(
			keyName = "attackSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = attackSkill
	)
	default boolean attackSunday() { return false; }

	@ConfigItem(
			keyName = "attackStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = attackSkill
	)
	default Hour attackStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "attackEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = attackSkill
	)
	default Hour attackEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "attackDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = attackSkill
	)
	default DayCadence attackDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "attackCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = attackSkill
	)
	default DayCadence attackDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "AttackCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = attackSkill
	)
	default String attackPattens() { return ""; }


	@ConfigSection(
			name = "Strength",
			description = "Strength Skill",
			position = 20,
			closedByDefault = true
	)
	String strengthSkill = "strengthSkill";

	@ConfigItem(
			keyName = "enableStrength",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not strength is enabled.",
			section = strengthSkill
	)
	default boolean enableStrengthSkill() { return false; }

	@ConfigItem(
			keyName = "strengthXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = strengthSkill
	)
	default int strengthXpGoal() { return 1; }

	@ConfigItem(
			keyName = "strengthResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = strengthSkill
	)
	default ResetType strengthResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "strengthMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = strengthSkill
	)
	default boolean strengthMonday() { return false; }

	@ConfigItem(
			keyName = "strengthTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = strengthSkill
	)
	default boolean strengthTuesday() { return false; }

	@ConfigItem(
			keyName = "strengthWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = strengthSkill
	)
	default boolean strengthWednesday() { return false; }

	@ConfigItem(
			keyName = "strengthThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = strengthSkill
	)
	default boolean strengthThursday() { return false; }

	@ConfigItem(
			keyName = "strengthFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = strengthSkill
	)
	default boolean strengthFriday() { return false; }

	@ConfigItem(
			keyName = "strengthSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = strengthSkill
	)
	default boolean strengthSaturday() { return false; }

	@ConfigItem(
			keyName = "strengthSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = strengthSkill
	)
	default boolean strengthSunday() { return false; }

	@ConfigItem(
			keyName = "strengthStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = strengthSkill
	)
	default Hour strengthStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "strengthEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = strengthSkill
	)
	default Hour strengthEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "strengthDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = strengthSkill
	)
	default DayCadence strengthDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "strengthCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = strengthSkill
	)
	default DayCadence strengthDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "StrengthCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = strengthSkill
	)
	default String strengthPattens() { return ""; }


	@ConfigSection(
			name = "Defense",
			description = "Defense Skill",
			position = 20,
			closedByDefault = true
	)
	String defenseSkill = "defenseSkill";

	@ConfigItem(
			keyName = "enableDefense",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not defense is enabled.",
			section = defenseSkill
	)
	default boolean enableDefenseSkill() { return false; }

	@ConfigItem(
			keyName = "defenseXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = defenseSkill
	)
	default int defenseXpGoal() { return 1; }

	@ConfigItem(
			keyName = "defenseResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = defenseSkill
	)
	default ResetType defenseResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "defenseMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = defenseSkill
	)
	default boolean defenseMonday() { return false; }

	@ConfigItem(
			keyName = "defenseTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = defenseSkill
	)
	default boolean defenseTuesday() { return false; }

	@ConfigItem(
			keyName = "defenseWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = defenseSkill
	)
	default boolean defenseWednesday() { return false; }

	@ConfigItem(
			keyName = "defenseThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = defenseSkill
	)
	default boolean defenseThursday() { return false; }

	@ConfigItem(
			keyName = "defenseFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = defenseSkill
	)
	default boolean defenseFriday() { return false; }

	@ConfigItem(
			keyName = "defenseSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = defenseSkill
	)
	default boolean defenseSaturday() { return false; }

	@ConfigItem(
			keyName = "defenseSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = defenseSkill
	)
	default boolean defenseSunday() { return false; }

	@ConfigItem(
			keyName = "defenseStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = defenseSkill
	)
	default Hour defenseStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "defenseEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = defenseSkill
	)
	default Hour defenseEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "defenseDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = defenseSkill
	)
	default DayCadence defenseDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "defenseCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = defenseSkill
	)
	default DayCadence defenseDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "DefenseCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = defenseSkill
	)
	default String defensePattens() { return ""; }


	@ConfigSection(
			name = "Ranged",
			description = "Ranged Skill",
			position = 20,
			closedByDefault = true
	)
	String rangedSkill = "rangedSkill";

	@ConfigItem(
			keyName = "enableRanged",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not ranged is enabled.",
			section = rangedSkill
	)
	default boolean enableRangedSkill() { return false; }

	@ConfigItem(
			keyName = "rangedXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = rangedSkill
	)
	default int rangedXpGoal() { return 1; }

	@ConfigItem(
			keyName = "rangedResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = rangedSkill
	)
	default ResetType rangedResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "rangedMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = rangedSkill
	)
	default boolean rangedMonday() { return false; }

	@ConfigItem(
			keyName = "rangedTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = rangedSkill
	)
	default boolean rangedTuesday() { return false; }

	@ConfigItem(
			keyName = "rangedWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = rangedSkill
	)
	default boolean rangedWednesday() { return false; }

	@ConfigItem(
			keyName = "rangedThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = rangedSkill
	)
	default boolean rangedThursday() { return false; }

	@ConfigItem(
			keyName = "rangedFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = rangedSkill
	)
	default boolean rangedFriday() { return false; }

	@ConfigItem(
			keyName = "rangedSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = rangedSkill
	)
	default boolean rangedSaturday() { return false; }

	@ConfigItem(
			keyName = "rangedSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = rangedSkill
	)
	default boolean rangedSunday() { return false; }

	@ConfigItem(
			keyName = "rangedStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = rangedSkill
	)
	default Hour rangedStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "rangedEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = rangedSkill
	)
	default Hour rangedEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "rangedDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = rangedSkill
	)
	default DayCadence rangedDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "rangedCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = rangedSkill
	)
	default DayCadence rangedDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "RangedCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = rangedSkill
	)
	default String rangedPattens() { return ""; }


	@ConfigSection(
			name = "Prayer",
			description = "Prayer Skill",
			position = 20,
			closedByDefault = true
	)
	String prayerSkill = "prayerSkill";

	@ConfigItem(
			keyName = "enablePrayer",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not prayer is enabled.",
			section = prayerSkill
	)
	default boolean enablePrayerSkill() { return false; }

	@ConfigItem(
			keyName = "prayerXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = prayerSkill
	)
	default int prayerXpGoal() { return 1; }

	@ConfigItem(
			keyName = "prayerResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = prayerSkill
	)
	default ResetType prayerResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "prayerMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = prayerSkill
	)
	default boolean prayerMonday() { return false; }

	@ConfigItem(
			keyName = "prayerTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = prayerSkill
	)
	default boolean prayerTuesday() { return false; }

	@ConfigItem(
			keyName = "prayerWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = prayerSkill
	)
	default boolean prayerWednesday() { return false; }

	@ConfigItem(
			keyName = "prayerThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = prayerSkill
	)
	default boolean prayerThursday() { return false; }

	@ConfigItem(
			keyName = "prayerFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = prayerSkill
	)
	default boolean prayerFriday() { return false; }

	@ConfigItem(
			keyName = "prayerSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = prayerSkill
	)
	default boolean prayerSaturday() { return false; }

	@ConfigItem(
			keyName = "prayerSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = prayerSkill
	)
	default boolean prayerSunday() { return false; }

	@ConfigItem(
			keyName = "prayerStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = prayerSkill
	)
	default Hour prayerStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "prayerEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = prayerSkill
	)
	default Hour prayerEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "prayerDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = prayerSkill
	)
	default DayCadence prayerDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "prayerCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = prayerSkill
	)
	default DayCadence prayerDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "PrayerCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = prayerSkill
	)
	default String prayerPattens() { return ""; }


	@ConfigSection(
			name = "Magic",
			description = "Magic Skill",
			position = 20,
			closedByDefault = true
	)
	String magicSkill = "magicSkill";

	@ConfigItem(
			keyName = "enableMagic",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not magic is enabled.",
			section = magicSkill
	)
	default boolean enableMagicSkill() { return false; }

	@ConfigItem(
			keyName = "magicXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = magicSkill
	)
	default int magicXpGoal() { return 1; }

	@ConfigItem(
			keyName = "magicResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = magicSkill
	)
	default ResetType magicResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "magicMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = magicSkill
	)
	default boolean magicMonday() { return false; }

	@ConfigItem(
			keyName = "magicTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = magicSkill
	)
	default boolean magicTuesday() { return false; }

	@ConfigItem(
			keyName = "magicWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = magicSkill
	)
	default boolean magicWednesday() { return false; }

	@ConfigItem(
			keyName = "magicThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = magicSkill
	)
	default boolean magicThursday() { return false; }

	@ConfigItem(
			keyName = "magicFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = magicSkill
	)
	default boolean magicFriday() { return false; }

	@ConfigItem(
			keyName = "magicSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = magicSkill
	)
	default boolean magicSaturday() { return false; }

	@ConfigItem(
			keyName = "magicSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = magicSkill
	)
	default boolean magicSunday() { return false; }

	@ConfigItem(
			keyName = "magicStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = magicSkill
	)
	default Hour magicStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "magicEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = magicSkill
	)
	default Hour magicEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "magicDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = magicSkill
	)
	default DayCadence magicDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "magicCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = magicSkill
	)
	default DayCadence magicDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "MagicCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = magicSkill
	)
	default String magicPattens() { return ""; }


	@ConfigSection(
			name = "Runecrafting",
			description = "Runecrafting Skill",
			position = 20,
			closedByDefault = true
	)
	String runecraftingSkill = "runecraftingSkill";

	@ConfigItem(
			keyName = "enableRunecrafting",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not runecrafting is enabled.",
			section = runecraftingSkill
	)
	default boolean enableRunecraftingSkill() { return false; }

	@ConfigItem(
			keyName = "runecraftingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = runecraftingSkill
	)
	default int runecraftingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "runecraftingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = runecraftingSkill
	)
	default ResetType runecraftingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "runecraftingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = runecraftingSkill
	)
	default boolean runecraftingMonday() { return false; }

	@ConfigItem(
			keyName = "runecraftingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = runecraftingSkill
	)
	default boolean runecraftingTuesday() { return false; }

	@ConfigItem(
			keyName = "runecraftingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = runecraftingSkill
	)
	default boolean runecraftingWednesday() { return false; }

	@ConfigItem(
			keyName = "runecraftingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = runecraftingSkill
	)
	default boolean runecraftingThursday() { return false; }

	@ConfigItem(
			keyName = "runecraftingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = runecraftingSkill
	)
	default boolean runecraftingFriday() { return false; }

	@ConfigItem(
			keyName = "runecraftingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = runecraftingSkill
	)
	default boolean runecraftingSaturday() { return false; }

	@ConfigItem(
			keyName = "runecraftingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = runecraftingSkill
	)
	default boolean runecraftingSunday() { return false; }

	@ConfigItem(
			keyName = "runecraftingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = runecraftingSkill
	)
	default Hour runecraftingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "runecraftingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = runecraftingSkill
	)
	default Hour runecraftingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "runecraftingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = runecraftingSkill
	)
	default DayCadence runecraftingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "runecraftingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = runecraftingSkill
	)
	default DayCadence runecraftingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "RunecraftingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = runecraftingSkill
	)
	default String runecraftingPattens() { return ""; }


	@ConfigSection(
			name = "Construction",
			description = "Construction Skill",
			position = 20,
			closedByDefault = true
	)
	String constructionSkill = "constructionSkill";

	@ConfigItem(
			keyName = "enableConstruction",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not construction is enabled.",
			section = constructionSkill
	)
	default boolean enableConstructionSkill() { return false; }

	@ConfigItem(
			keyName = "constructionXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = constructionSkill
	)
	default int constructionXpGoal() { return 1; }

	@ConfigItem(
			keyName = "constructionResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = constructionSkill
	)
	default ResetType constructionResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "constructionMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = constructionSkill
	)
	default boolean constructionMonday() { return false; }

	@ConfigItem(
			keyName = "constructionTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = constructionSkill
	)
	default boolean constructionTuesday() { return false; }

	@ConfigItem(
			keyName = "constructionWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = constructionSkill
	)
	default boolean constructionWednesday() { return false; }

	@ConfigItem(
			keyName = "constructionThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = constructionSkill
	)
	default boolean constructionThursday() { return false; }

	@ConfigItem(
			keyName = "constructionFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = constructionSkill
	)
	default boolean constructionFriday() { return false; }

	@ConfigItem(
			keyName = "constructionSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = constructionSkill
	)
	default boolean constructionSaturday() { return false; }

	@ConfigItem(
			keyName = "constructionSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = constructionSkill
	)
	default boolean constructionSunday() { return false; }

	@ConfigItem(
			keyName = "constructionStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = constructionSkill
	)
	default Hour constructionStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "constructionEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = constructionSkill
	)
	default Hour constructionEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "constructionDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = constructionSkill
	)
	default DayCadence constructionDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "constructionCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = constructionSkill
	)
	default DayCadence constructionDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "ConstructionCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = constructionSkill
	)
	default String constructionPattens() { return ""; }


	@ConfigSection(
			name = "Hitpoints",
			description = "Hitpoints Skill",
			position = 20,
			closedByDefault = true
	)
	String hitpointsSkill = "hitpointsSkill";

	@ConfigItem(
			keyName = "enableHitpoints",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not hitpoints is enabled.",
			section = hitpointsSkill
	)
	default boolean enableHitpointsSkill() { return false; }

	@ConfigItem(
			keyName = "hitpointsXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = hitpointsSkill
	)
	default int hitpointsXpGoal() { return 1; }

	@ConfigItem(
			keyName = "hitpointsResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = hitpointsSkill
	)
	default ResetType hitpointsResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "hitpointsMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = hitpointsSkill
	)
	default boolean hitpointsMonday() { return false; }

	@ConfigItem(
			keyName = "hitpointsTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = hitpointsSkill
	)
	default boolean hitpointsTuesday() { return false; }

	@ConfigItem(
			keyName = "hitpointsWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = hitpointsSkill
	)
	default boolean hitpointsWednesday() { return false; }

	@ConfigItem(
			keyName = "hitpointsThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = hitpointsSkill
	)
	default boolean hitpointsThursday() { return false; }

	@ConfigItem(
			keyName = "hitpointsFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = hitpointsSkill
	)
	default boolean hitpointsFriday() { return false; }

	@ConfigItem(
			keyName = "hitpointsSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = hitpointsSkill
	)
	default boolean hitpointsSaturday() { return false; }

	@ConfigItem(
			keyName = "hitpointsSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = hitpointsSkill
	)
	default boolean hitpointsSunday() { return false; }

	@ConfigItem(
			keyName = "hitpointsStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = hitpointsSkill
	)
	default Hour hitpointsStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "hitpointsEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = hitpointsSkill
	)
	default Hour hitpointsEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "hitpointsDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = hitpointsSkill
	)
	default DayCadence hitpointsDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "hitpointsCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = hitpointsSkill
	)
	default DayCadence hitpointsDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "HitpointsCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = hitpointsSkill
	)
	default String hitpointsPattens() { return ""; }


	@ConfigSection(
			name = "Agility",
			description = "Agility Skill",
			position = 20,
			closedByDefault = true
	)
	String agilitySkill = "agilitySkill";

	@ConfigItem(
			keyName = "enableAgility",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not agility is enabled.",
			section = agilitySkill
	)
	default boolean enableAgilitySkill() { return false; }

	@ConfigItem(
			keyName = "agilityXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = agilitySkill
	)
	default int agilityXpGoal() { return 1; }

	@ConfigItem(
			keyName = "agilityResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = agilitySkill
	)
	default ResetType agilityResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "agilityMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = agilitySkill
	)
	default boolean agilityMonday() { return false; }

	@ConfigItem(
			keyName = "agilityTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = agilitySkill
	)
	default boolean agilityTuesday() { return false; }

	@ConfigItem(
			keyName = "agilityWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = agilitySkill
	)
	default boolean agilityWednesday() { return false; }

	@ConfigItem(
			keyName = "agilityThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = agilitySkill
	)
	default boolean agilityThursday() { return false; }

	@ConfigItem(
			keyName = "agilityFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = agilitySkill
	)
	default boolean agilityFriday() { return false; }

	@ConfigItem(
			keyName = "agilitySaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = agilitySkill
	)
	default boolean agilitySaturday() { return false; }

	@ConfigItem(
			keyName = "agilitySunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = agilitySkill
	)
	default boolean agilitySunday() { return false; }

	@ConfigItem(
			keyName = "agilityStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = agilitySkill
	)
	default Hour agilityStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "agilityEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = agilitySkill
	)
	default Hour agilityEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "agilityDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = agilitySkill
	)
	default DayCadence agilityDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "agilityCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = agilitySkill
	)
	default DayCadence agilityDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "AgilityCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = agilitySkill
	)
	default String agilityPattens() { return ""; }


	@ConfigSection(
			name = "Herblore",
			description = "Herblore Skill",
			position = 20,
			closedByDefault = true
	)
	String herbloreSkill = "herbloreSkill";

	@ConfigItem(
			keyName = "enableHerblore",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not herblore is enabled.",
			section = herbloreSkill
	)
	default boolean enableHerbloreSkill() { return false; }

	@ConfigItem(
			keyName = "herbloreXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = herbloreSkill
	)
	default int herbloreXpGoal() { return 1; }

	@ConfigItem(
			keyName = "herbloreResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = herbloreSkill
	)
	default ResetType herbloreResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "herbloreMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = herbloreSkill
	)
	default boolean herbloreMonday() { return false; }

	@ConfigItem(
			keyName = "herbloreTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = herbloreSkill
	)
	default boolean herbloreTuesday() { return false; }

	@ConfigItem(
			keyName = "herbloreWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = herbloreSkill
	)
	default boolean herbloreWednesday() { return false; }

	@ConfigItem(
			keyName = "herbloreThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = herbloreSkill
	)
	default boolean herbloreThursday() { return false; }

	@ConfigItem(
			keyName = "herbloreFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = herbloreSkill
	)
	default boolean herbloreFriday() { return false; }

	@ConfigItem(
			keyName = "herbloreSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = herbloreSkill
	)
	default boolean herbloreSaturday() { return false; }

	@ConfigItem(
			keyName = "herbloreSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = herbloreSkill
	)
	default boolean herbloreSunday() { return false; }

	@ConfigItem(
			keyName = "herbloreStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = herbloreSkill
	)
	default Hour herbloreStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "herbloreEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = herbloreSkill
	)
	default Hour herbloreEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "herbloreDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = herbloreSkill
	)
	default DayCadence herbloreDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "herbloreCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = herbloreSkill
	)
	default DayCadence herbloreDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "HerbloreCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = herbloreSkill
	)
	default String herblorePattens() { return ""; }


	@ConfigSection(
			name = "Thieving",
			description = "Thieving Skill",
			position = 20,
			closedByDefault = true
	)
	String thievingSkill = "thievingSkill";

	@ConfigItem(
			keyName = "enableThieving",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not thieving is enabled.",
			section = thievingSkill
	)
	default boolean enableThievingSkill() { return false; }

	@ConfigItem(
			keyName = "thievingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = thievingSkill
	)
	default int thievingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "thievingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = thievingSkill
	)
	default ResetType thievingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "thievingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = thievingSkill
	)
	default boolean thievingMonday() { return false; }

	@ConfigItem(
			keyName = "thievingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = thievingSkill
	)
	default boolean thievingTuesday() { return false; }

	@ConfigItem(
			keyName = "thievingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = thievingSkill
	)
	default boolean thievingWednesday() { return false; }

	@ConfigItem(
			keyName = "thievingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = thievingSkill
	)
	default boolean thievingThursday() { return false; }

	@ConfigItem(
			keyName = "thievingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = thievingSkill
	)
	default boolean thievingFriday() { return false; }

	@ConfigItem(
			keyName = "thievingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = thievingSkill
	)
	default boolean thievingSaturday() { return false; }

	@ConfigItem(
			keyName = "thievingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = thievingSkill
	)
	default boolean thievingSunday() { return false; }

	@ConfigItem(
			keyName = "thievingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = thievingSkill
	)
	default Hour thievingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "thievingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = thievingSkill
	)
	default Hour thievingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "thievingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = thievingSkill
	)
	default DayCadence thievingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "thievingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = thievingSkill
	)
	default DayCadence thievingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "ThievingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = thievingSkill
	)
	default String thievingPattens() { return ""; }


	@ConfigSection(
			name = "Crafting",
			description = "Crafting Skill",
			position = 20,
			closedByDefault = true
	)
	String craftingSkill = "craftingSkill";

	@ConfigItem(
			keyName = "enableCrafting",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not crafting is enabled.",
			section = craftingSkill
	)
	default boolean enableCraftingSkill() { return false; }

	@ConfigItem(
			keyName = "craftingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = craftingSkill
	)
	default int craftingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "craftingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = craftingSkill
	)
	default ResetType craftingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "craftingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = craftingSkill
	)
	default boolean craftingMonday() { return false; }

	@ConfigItem(
			keyName = "craftingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = craftingSkill
	)
	default boolean craftingTuesday() { return false; }

	@ConfigItem(
			keyName = "craftingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = craftingSkill
	)
	default boolean craftingWednesday() { return false; }

	@ConfigItem(
			keyName = "craftingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = craftingSkill
	)
	default boolean craftingThursday() { return false; }

	@ConfigItem(
			keyName = "craftingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = craftingSkill
	)
	default boolean craftingFriday() { return false; }

	@ConfigItem(
			keyName = "craftingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = craftingSkill
	)
	default boolean craftingSaturday() { return false; }

	@ConfigItem(
			keyName = "craftingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = craftingSkill
	)
	default boolean craftingSunday() { return false; }

	@ConfigItem(
			keyName = "craftingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = craftingSkill
	)
	default Hour craftingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "craftingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = craftingSkill
	)
	default Hour craftingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "craftingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = craftingSkill
	)
	default DayCadence craftingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "craftingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = craftingSkill
	)
	default DayCadence craftingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "CraftingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = craftingSkill
	)
	default String craftingPattens() { return ""; }


	@ConfigSection(
			name = "Fletching",
			description = "Fletching Skill",
			position = 20,
			closedByDefault = true
	)
	String fletchingSkill = "fletchingSkill";

	@ConfigItem(
			keyName = "enableFletching",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not fletching is enabled.",
			section = fletchingSkill
	)
	default boolean enableFletchingSkill() { return false; }

	@ConfigItem(
			keyName = "fletchingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = fletchingSkill
	)
	default int fletchingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "fletchingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = fletchingSkill
	)
	default ResetType fletchingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "fletchingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = fletchingSkill
	)
	default boolean fletchingMonday() { return false; }

	@ConfigItem(
			keyName = "fletchingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = fletchingSkill
	)
	default boolean fletchingTuesday() { return false; }

	@ConfigItem(
			keyName = "fletchingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = fletchingSkill
	)
	default boolean fletchingWednesday() { return false; }

	@ConfigItem(
			keyName = "fletchingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = fletchingSkill
	)
	default boolean fletchingThursday() { return false; }

	@ConfigItem(
			keyName = "fletchingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = fletchingSkill
	)
	default boolean fletchingFriday() { return false; }

	@ConfigItem(
			keyName = "fletchingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = fletchingSkill
	)
	default boolean fletchingSaturday() { return false; }

	@ConfigItem(
			keyName = "fletchingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = fletchingSkill
	)
	default boolean fletchingSunday() { return false; }

	@ConfigItem(
			keyName = "fletchingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = fletchingSkill
	)
	default Hour fletchingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "fletchingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = fletchingSkill
	)
	default Hour fletchingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "fletchingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = fletchingSkill
	)
	default DayCadence fletchingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "fletchingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = fletchingSkill
	)
	default DayCadence fletchingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "FletchingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = fletchingSkill
	)
	default String fletchingPattens() { return ""; }


	@ConfigSection(
			name = "Slayer",
			description = "Slayer Skill",
			position = 20,
			closedByDefault = true
	)
	String slayerSkill = "slayerSkill";

	@ConfigItem(
			keyName = "enableSlayer",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not slayer is enabled.",
			section = slayerSkill
	)
	default boolean enableSlayerSkill() { return false; }

	@ConfigItem(
			keyName = "slayerXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = slayerSkill
	)
	default int slayerXpGoal() { return 1; }

	@ConfigItem(
			keyName = "slayerResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = slayerSkill
	)
	default ResetType slayerResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "slayerMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = slayerSkill
	)
	default boolean slayerMonday() { return false; }

	@ConfigItem(
			keyName = "slayerTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = slayerSkill
	)
	default boolean slayerTuesday() { return false; }

	@ConfigItem(
			keyName = "slayerWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = slayerSkill
	)
	default boolean slayerWednesday() { return false; }

	@ConfigItem(
			keyName = "slayerThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = slayerSkill
	)
	default boolean slayerThursday() { return false; }

	@ConfigItem(
			keyName = "slayerFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = slayerSkill
	)
	default boolean slayerFriday() { return false; }

	@ConfigItem(
			keyName = "slayerSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = slayerSkill
	)
	default boolean slayerSaturday() { return false; }

	@ConfigItem(
			keyName = "slayerSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = slayerSkill
	)
	default boolean slayerSunday() { return false; }

	@ConfigItem(
			keyName = "slayerStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = slayerSkill
	)
	default Hour slayerStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "slayerEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = slayerSkill
	)
	default Hour slayerEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "slayerDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = slayerSkill
	)
	default DayCadence slayerDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "slayerCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = slayerSkill
	)
	default DayCadence slayerDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "SlayerCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = slayerSkill
	)
	default String slayerPattens() { return ""; }


	@ConfigSection(
			name = "Hunter",
			description = "Hunter Skill",
			position = 20,
			closedByDefault = true
	)
	String hunterSkill = "hunterSkill";

	@ConfigItem(
			keyName = "enableHunter",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not hunter is enabled.",
			section = hunterSkill
	)
	default boolean enableHunterSkill() { return false; }

	@ConfigItem(
			keyName = "hunterXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = hunterSkill
	)
	default int hunterXpGoal() { return 1; }

	@ConfigItem(
			keyName = "hunterResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = hunterSkill
	)
	default ResetType hunterResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "hunterMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = hunterSkill
	)
	default boolean hunterMonday() { return false; }

	@ConfigItem(
			keyName = "hunterTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = hunterSkill
	)
	default boolean hunterTuesday() { return false; }

	@ConfigItem(
			keyName = "hunterWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = hunterSkill
	)
	default boolean hunterWednesday() { return false; }

	@ConfigItem(
			keyName = "hunterThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = hunterSkill
	)
	default boolean hunterThursday() { return false; }

	@ConfigItem(
			keyName = "hunterFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = hunterSkill
	)
	default boolean hunterFriday() { return false; }

	@ConfigItem(
			keyName = "hunterSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = hunterSkill
	)
	default boolean hunterSaturday() { return false; }

	@ConfigItem(
			keyName = "hunterSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = hunterSkill
	)
	default boolean hunterSunday() { return false; }

	@ConfigItem(
			keyName = "hunterStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = hunterSkill
	)
	default Hour hunterStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "hunterEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = hunterSkill
	)
	default Hour hunterEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "hunterDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = hunterSkill
	)
	default DayCadence hunterDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "hunterCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = hunterSkill
	)
	default DayCadence hunterDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "HunterCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = hunterSkill
	)
	default String hunterPattens() { return ""; }


	@ConfigSection(
			name = "Mining",
			description = "Mining Skill",
			position = 20,
			closedByDefault = true
	)
	String miningSkill = "miningSkill";

	@ConfigItem(
			keyName = "enableMining",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not mining is enabled.",
			section = miningSkill
	)
	default boolean enableMiningSkill() { return false; }

	@ConfigItem(
			keyName = "miningXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = miningSkill
	)
	default int miningXpGoal() { return 1; }

	@ConfigItem(
			keyName = "miningResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = miningSkill
	)
	default ResetType miningResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "miningMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = miningSkill
	)
	default boolean miningMonday() { return false; }

	@ConfigItem(
			keyName = "miningTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = miningSkill
	)
	default boolean miningTuesday() { return false; }

	@ConfigItem(
			keyName = "miningWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = miningSkill
	)
	default boolean miningWednesday() { return false; }

	@ConfigItem(
			keyName = "miningThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = miningSkill
	)
	default boolean miningThursday() { return false; }

	@ConfigItem(
			keyName = "miningFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = miningSkill
	)
	default boolean miningFriday() { return false; }

	@ConfigItem(
			keyName = "miningSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = miningSkill
	)
	default boolean miningSaturday() { return false; }

	@ConfigItem(
			keyName = "miningSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = miningSkill
	)
	default boolean miningSunday() { return false; }

	@ConfigItem(
			keyName = "miningStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = miningSkill
	)
	default Hour miningStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "miningEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = miningSkill
	)
	default Hour miningEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "miningDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = miningSkill
	)
	default DayCadence miningDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "miningCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = miningSkill
	)
	default DayCadence miningDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "MiningCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = miningSkill
	)
	default String miningPattens() { return ""; }


	@ConfigSection(
			name = "Smithing",
			description = "Smithing Skill",
			position = 20,
			closedByDefault = true
	)
	String smithingSkill = "smithingSkill";

	@ConfigItem(
			keyName = "enableSmithing",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not smithing is enabled.",
			section = smithingSkill
	)
	default boolean enableSmithingSkill() { return false; }

	@ConfigItem(
			keyName = "smithingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = smithingSkill
	)
	default int smithingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "smithingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = smithingSkill
	)
	default ResetType smithingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "smithingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = smithingSkill
	)
	default boolean smithingMonday() { return false; }

	@ConfigItem(
			keyName = "smithingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = smithingSkill
	)
	default boolean smithingTuesday() { return false; }

	@ConfigItem(
			keyName = "smithingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = smithingSkill
	)
	default boolean smithingWednesday() { return false; }

	@ConfigItem(
			keyName = "smithingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = smithingSkill
	)
	default boolean smithingThursday() { return false; }

	@ConfigItem(
			keyName = "smithingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = smithingSkill
	)
	default boolean smithingFriday() { return false; }

	@ConfigItem(
			keyName = "smithingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = smithingSkill
	)
	default boolean smithingSaturday() { return false; }

	@ConfigItem(
			keyName = "smithingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = smithingSkill
	)
	default boolean smithingSunday() { return false; }

	@ConfigItem(
			keyName = "smithingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = smithingSkill
	)
	default Hour smithingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "smithingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = smithingSkill
	)
	default Hour smithingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "smithingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = smithingSkill
	)
	default DayCadence smithingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "smithingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = smithingSkill
	)
	default DayCadence smithingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "SmithingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = smithingSkill
	)
	default String smithingPattens() { return ""; }


	@ConfigSection(
			name = "Fishing",
			description = "Fishing Skill",
			position = 20,
			closedByDefault = true
	)
	String fishingSkill = "fishingSkill";

	@ConfigItem(
			keyName = "enableFishing",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not fishing is enabled.",
			section = fishingSkill
	)
	default boolean enableFishingSkill() { return false; }

	@ConfigItem(
			keyName = "fishingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = fishingSkill
	)
	default int fishingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "fishingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = fishingSkill
	)
	default ResetType fishingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "fishingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = fishingSkill
	)
	default boolean fishingMonday() { return false; }

	@ConfigItem(
			keyName = "fishingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = fishingSkill
	)
	default boolean fishingTuesday() { return false; }

	@ConfigItem(
			keyName = "fishingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = fishingSkill
	)
	default boolean fishingWednesday() { return false; }

	@ConfigItem(
			keyName = "fishingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = fishingSkill
	)
	default boolean fishingThursday() { return false; }

	@ConfigItem(
			keyName = "fishingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = fishingSkill
	)
	default boolean fishingFriday() { return false; }

	@ConfigItem(
			keyName = "fishingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = fishingSkill
	)
	default boolean fishingSaturday() { return false; }

	@ConfigItem(
			keyName = "fishingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = fishingSkill
	)
	default boolean fishingSunday() { return false; }

	@ConfigItem(
			keyName = "fishingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = fishingSkill
	)
	default Hour fishingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "fishingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = fishingSkill
	)
	default Hour fishingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "fishingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = fishingSkill
	)
	default DayCadence fishingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "fishingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = fishingSkill
	)
	default DayCadence fishingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "FishingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = fishingSkill
	)
	default String fishingPattens() { return ""; }


	@ConfigSection(
			name = "Cooking",
			description = "Cooking Skill",
			position = 20,
			closedByDefault = true
	)
	String cookingSkill = "cookingSkill";

	@ConfigItem(
			keyName = "enableCooking",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not cooking is enabled.",
			section = cookingSkill
	)
	default boolean enableCookingSkill() { return false; }

	@ConfigItem(
			keyName = "cookingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = cookingSkill
	)
	default int cookingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "cookingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = cookingSkill
	)
	default ResetType cookingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "cookingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = cookingSkill
	)
	default boolean cookingMonday() { return false; }

	@ConfigItem(
			keyName = "cookingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = cookingSkill
	)
	default boolean cookingTuesday() { return false; }

	@ConfigItem(
			keyName = "cookingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = cookingSkill
	)
	default boolean cookingWednesday() { return false; }

	@ConfigItem(
			keyName = "cookingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = cookingSkill
	)
	default boolean cookingThursday() { return false; }

	@ConfigItem(
			keyName = "cookingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = cookingSkill
	)
	default boolean cookingFriday() { return false; }

	@ConfigItem(
			keyName = "cookingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = cookingSkill
	)
	default boolean cookingSaturday() { return false; }

	@ConfigItem(
			keyName = "cookingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = cookingSkill
	)
	default boolean cookingSunday() { return false; }

	@ConfigItem(
			keyName = "cookingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = cookingSkill
	)
	default Hour cookingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "cookingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = cookingSkill
	)
	default Hour cookingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "cookingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = cookingSkill
	)
	default DayCadence cookingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "cookingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = cookingSkill
	)
	default DayCadence cookingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "CookingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = cookingSkill
	)
	default String cookingPattens() { return ""; }


	@ConfigSection(
			name = "Firemaking",
			description = "Firemaking Skill",
			position = 20,
			closedByDefault = true
	)
	String firemakingSkill = "firemakingSkill";

	@ConfigItem(
			keyName = "enableFiremaking",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not firemaking is enabled.",
			section = firemakingSkill
	)
	default boolean enableFiremakingSkill() { return false; }

	@ConfigItem(
			keyName = "firemakingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = firemakingSkill
	)
	default int firemakingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "firemakingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = firemakingSkill
	)
	default ResetType firemakingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "firemakingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = firemakingSkill
	)
	default boolean firemakingMonday() { return false; }

	@ConfigItem(
			keyName = "firemakingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = firemakingSkill
	)
	default boolean firemakingTuesday() { return false; }

	@ConfigItem(
			keyName = "firemakingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = firemakingSkill
	)
	default boolean firemakingWednesday() { return false; }

	@ConfigItem(
			keyName = "firemakingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = firemakingSkill
	)
	default boolean firemakingThursday() { return false; }

	@ConfigItem(
			keyName = "firemakingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = firemakingSkill
	)
	default boolean firemakingFriday() { return false; }

	@ConfigItem(
			keyName = "firemakingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = firemakingSkill
	)
	default boolean firemakingSaturday() { return false; }

	@ConfigItem(
			keyName = "firemakingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = firemakingSkill
	)
	default boolean firemakingSunday() { return false; }

	@ConfigItem(
			keyName = "firemakingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = firemakingSkill
	)
	default Hour firemakingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "firemakingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = firemakingSkill
	)
	default Hour firemakingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "firemakingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = firemakingSkill
	)
	default DayCadence firemakingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "firemakingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = firemakingSkill
	)
	default DayCadence firemakingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "FiremakingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = firemakingSkill
	)
	default String firemakingPattens() { return ""; }


	@ConfigSection(
			name = "Woodcutting",
			description = "Woodcutting Skill",
			position = 20,
			closedByDefault = true
	)
	String woodcuttingSkill = "woodcuttingSkill";

	@ConfigItem(
			keyName = "enableWoodcutting",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not woodcutting is enabled.",
			section = woodcuttingSkill
	)
	default boolean enableWoodcuttingSkill() { return false; }

	@ConfigItem(
			keyName = "woodcuttingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = woodcuttingSkill
	)
	default int woodcuttingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "woodcuttingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = woodcuttingSkill
	)
	default ResetType woodcuttingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "woodcuttingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingMonday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingTuesday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingWednesday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingThursday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingFriday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingSaturday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = woodcuttingSkill
	)
	default boolean woodcuttingSunday() { return false; }

	@ConfigItem(
			keyName = "woodcuttingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = woodcuttingSkill
	)
	default Hour woodcuttingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "woodcuttingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = woodcuttingSkill
	)
	default Hour woodcuttingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "woodcuttingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = woodcuttingSkill
	)
	default DayCadence woodcuttingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "woodcuttingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = woodcuttingSkill
	)
	default DayCadence woodcuttingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "WoodcuttingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = woodcuttingSkill
	)
	default String woodcuttingPattens() { return ""; }


	@ConfigSection(
			name = "Farming",
			description = "Farming Skill",
			position = 20,
			closedByDefault = true
	)
	String farmingSkill = "farmingSkill";

	@ConfigItem(
			keyName = "enableFarming",
			position = 0,
			name = "Enabled",
			description = "Configures whether or not farming is enabled.",
			section = farmingSkill
	)
	default boolean enableFarmingSkill() { return false; }

	@ConfigItem(
			keyName = "farmingXpGoal",
			position = 2,
			name = "Target Xp",
			description = "Configures xp goal.",
			section = farmingSkill
	)
	default int farmingXpGoal() { return 1; }

	@ConfigItem(
			keyName = "farmingResetType",
			position = 3,
			name = "Reset Interval",
			description = "Configures how often progress resets.",
			section = farmingSkill
	)
	default ResetType farmingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "farmingMonday",
			position = 4,
			name = "Monday",
			description = "Configures whether or not progress is tracked on Mondays.",
			section = farmingSkill
	)
	default boolean farmingMonday() { return false; }

	@ConfigItem(
			keyName = "farmingTuesday",
			position = 5,
			name = "Tuesday",
			description = "Configures whether or not progress is tracked on Tuesdays.",
			section = farmingSkill
	)
	default boolean farmingTuesday() { return false; }

	@ConfigItem(
			keyName = "farmingWednesday",
			position = 6,
			name = "Wednesday",
			description = "Configures whether or not progress is tracked on Wednesdays.",
			section = farmingSkill
	)
	default boolean farmingWednesday() { return false; }

	@ConfigItem(
			keyName = "farmingThursday",
			position = 7,
			name = "Thursday",
			description = "Configures whether or not progress is tracked on Thursdays.",
			section = farmingSkill
	)
	default boolean farmingThursday() { return false; }

	@ConfigItem(
			keyName = "farmingFriday",
			position = 8,
			name = "Friday",
			description = "Configures whether or not progress is tracked on Fridays.",
			section = farmingSkill
	)
	default boolean farmingFriday() { return false; }

	@ConfigItem(
			keyName = "farmingSaturday",
			position = 9,
			name = "Saturday",
			description = "Configures whether or not progress is tracked on Saturdays.",
			section = farmingSkill
	)
	default boolean farmingSaturday() { return false; }

	@ConfigItem(
			keyName = "farmingSunday",
			position = 10,
			name = "Sunday",
			description = "Configures whether or not progress is tracked on Sundays.",
			section = farmingSkill
	)
	default boolean farmingSunday() { return false; }

	@ConfigItem(
			keyName = "farmingStartTime",
			position = 11,
			name = "Start Time",
			description = "Configures start time for when progress is tracked each day.",
			section = farmingSkill
	)
	default Hour farmingStartTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "farmingEndTime",
			position = 12,
			name = "End Time",
			description = "Configures end time for when progress is tracked each day.",
			section = farmingSkill
	)
	default Hour farmingEndTime() { return Hour.NONE; }

	@ConfigItem(
			keyName = "farmingDayCadence",
			position = 13,
			name = "Day Cadence",
			description = "Configures day cadence for when progress is tracked. For example 1, 2, 3, 4, 5.",
			section = farmingSkill
	)
	default DayCadence farmingDayCadence() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "farmingCadenceDay",
			position = 14,
			name = "Cadence Day",
			description = "Configures on which day in cadence progress is tracked.",
			section = farmingSkill
	)
	default DayCadence farmingDayCadenceDay() { return DayCadence.NONE; }

	@ConfigItem(
			keyName = "FarmingCustomPatterns",
			position = 15,
			name = "Custom Patterns",
			description = "Configures custom track patterns. See plugin page about making custom patterns.",
			section = farmingSkill
	)
	default String farmingPattens() { return ""; }
}