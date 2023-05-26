package com.ericversteeg;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(XpGoalsConfig.GROUP)
public interface XpGoalsConfig extends Config
{
	String GROUP = "xpgoals";

	@ConfigItem(
			keyName = "anchorX",
			name = "Anchor X Position",
			description = "Configures the x position for the xp bars."
	)
	default int anchorX() { return 0; }

	@ConfigItem(
			keyName = "anchorY",
			name = "Anchor Y Position",
			description = "Configures the y position for the xp bars."
	)
	default int anchorY() { return 0; }

	@ConfigItem(
			keyName = "textOffsetX",
			name = "Text Offset X Position",
			description = "Configures the x position offset for text."
	)
	default int textOffsetX() { return 0; }

	@ConfigItem(
			keyName = "textPrecision",
			name = "Text Precision",
			description = "Configures the decimal places."
	)
	default int textPrecision() { return 3; }

	@ConfigItem(
			keyName = "textOffsetY",
			name = "Text Offset Y Position",
			description = "Configures the y position offset for text."
	)
	default int textOffsetY() { return 0; }

	@ConfigItem(
			keyName = "textOffsetXNegative",
			name = "Text Offset X Negative",
			description = "Configures the x position offset for text."
	)
	default boolean textOffsetXNegative() { return false; }

	@ConfigItem(
			keyName = "remainingXp",
			name = "Remaining Xp",
			description = "Configures whether or not to show remaining xp."
	)
	default boolean showRemainingXp() { return false; }

	@ConfigItem(
			keyName = "showText",
			name = "Show Text",
			description = "Configures whether or not to show progress / remaining xp text."
	)
	default boolean showText() { return false; }

	@ConfigItem(
			keyName = "textOffsetYNegative",
			name = "Text Offset Y Negative",
			description = "Configures the y position offset for text."
	)
	default boolean textOffsetYNegative() { return false; }

	@ConfigItem(
			keyName = "barVerticalSpacing",
			name = "Bar Vertical Spacing",
			description = "Configures the space between bars."
	)
	default int barVerticalSpacing() { return 30; }

	@ConfigItem(
			keyName = "barWidth",
			name = "Bar Width",
			description = "Configures the width of xp bars."
	)
	default int barWidth() { return 80; }

	@ConfigItem(
			keyName = "barHeight",
			name = "Bar Height",
			description = "Configures the height of xp bars."
	)
	default int barHeight() { return 6; }

	@Alpha
	@ConfigItem(
			keyName = "backgroundColor",
			name = "Background Color",
			description = "Configures the background color."
	)
	default Color bgColor() { return Color.decode("#1b1b1b"); }

	@Alpha
	@ConfigItem(
			keyName = "bgOuterBorderColor",
			name = "Outer Border Color",
			description = "Configures the outer border color."
	)
	default Color bgOuterBorder() { return Color.decode("#1b1b1b"); }

	@Alpha
	@ConfigItem(
			keyName = "bgInnerBorderColor",
			name = "Inner Border Color",
			description = "Configures the inner border color."
	)
	default Color bgInnerBorder() { return Color.decode("#1b1b1b"); }

	@ConfigSection(
			name = "Mining",
			description = "Mining Skill",
			position = 0,
			closedByDefault = true
	)
	String miningSkill = "miningSkill";

	@ConfigItem(
			keyName = "enableMining",
			name = "Enabled",
			description = "Configures whether or not mining skill is enabled.",
			section = miningSkill
	)
	default boolean enableMiningSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "miningResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = miningSkill
	)
	default ResetType miningResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "miningProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = miningSkill
	)
	default Color miningProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "miningXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = miningSkill
	)
	default int miningXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "MiningVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = miningSkill
	)
	default String miningPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Runecrafting",
			description = "Runecrafting Skill",
			position = 0,
			closedByDefault = true
	)
	String runecraftingSkill = "runecraftingSkill";

	@ConfigItem(
			keyName = "enableRunecrafting",
			name = "Enabled",
			description = "Configures whether or not Runecrafting skill is enabled.",
			section = runecraftingSkill
	)
	default boolean enableRunecraftingSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "runecraftingResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = runecraftingSkill
	)
	default ResetType runecraftingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "runecraftingProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = runecraftingSkill
	)
	default Color runecraftingProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "runecraftingXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = runecraftingSkill
	)
	default int runecraftingXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "runecraftingVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = runecraftingSkill
	)
	default String runecraftingPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Agility",
			description = "Agility Skill",
			position = 0,
			closedByDefault = true
	)
	String agilitySkill = "agilitySkill";

	@ConfigItem(
			keyName = "enableAgility",
			name = "Enabled",
			description = "Configures whether or not agility skill is enabled.",
			section = agilitySkill
	)
	default boolean enableAgilitySkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "agilityResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = agilitySkill
	)
	default ResetType agilityResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "agilityProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = agilitySkill
	)
	default Color agilityProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "agilityXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = agilitySkill
	)
	default int agilityXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "agilityVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = agilitySkill
	)
	default String agilityPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Fishing",
			description = "Fishing Skill",
			position = 0,
			closedByDefault = true
	)
	String fishingSkill = "fishingSkill";

	@ConfigItem(
			keyName = "enableFishing",
			name = "Enabled",
			description = "Configures whether or not fishing skill is enabled.",
			section = fishingSkill
	)
	default boolean enableFishingSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "fishingResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = fishingSkill
	)
	default ResetType fishingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "fishingProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = fishingSkill
	)
	default Color fishingProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "fishingXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = fishingSkill
	)
	default int fishingXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "fishingVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = fishingSkill
	)
	default String fishingPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Woodcutting",
			description = "Woodcutting Skill",
			position = 0,
			closedByDefault = true
	)
	String woodcuttingSkill = "woodcuttingSkill";

	@ConfigItem(
			keyName = "enableWoodcutting",
			name = "Enabled",
			description = "Configures whether or not woodcutting skill is enabled.",
			section = woodcuttingSkill
	)
	default boolean enableWoodcuttingSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "woodcuttingResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = woodcuttingSkill
	)
	default ResetType woodcuttingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "woodcuttingProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = woodcuttingSkill
	)
	default Color woodcuttingProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "woodcuttingXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = woodcuttingSkill
	)
	default int woodcuttingXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "woodcuttingVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = woodcuttingSkill
	)
	default String woodcuttingPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Farming",
			description = "Farming Skill",
			position = 0,
			closedByDefault = true
	)
	String farmingSkill = "farmingSkill";

	@ConfigItem(
			keyName = "enableFarming",
			name = "Enabled",
			description = "Configures whether or not farming skill is enabled.",
			section = farmingSkill
	)
	default boolean enableFarmingSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "farmingResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = farmingSkill
	)
	default ResetType farmingResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "farmingProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = farmingSkill
	)
	default Color farmingProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "farmingXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = farmingSkill
	)
	default int farmingXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "farmingVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = farmingSkill
	)
	default String farmingPattens()
	{
		return "";
	}

	@ConfigSection(
			name = "Ranged",
			description = "Ranged Skill",
			position = 0,
			closedByDefault = true
	)
	String rangedSkill = "rangedSkill";

	@ConfigItem(
			keyName = "enableRanged",
			name = "Enabled",
			description = "Configures whether or not ranged skill is enabled.",
			section = rangedSkill
	)
	default boolean enableRangedSkill()
	{
		return false;
	}

	@ConfigItem(
			keyName = "rangedResetType",
			name = "Reset Interval",
			description = "Configured how often skill progress resets.",
			section = rangedSkill
	)
	default ResetType rangedResetType() { return ResetType.DAILY; }

	@ConfigItem(
			keyName = "rangedProgressColor",
			name = "Progress Color",
			description = "Configures the progress color.",
			section = rangedSkill
	)
	default Color rangedProgressColor()
	{
		return Color.decode("#30FCAB");
	}

	@ConfigItem(
			keyName = "rangedXpGoal",
			name = "Target Xp",
			description = "Configures the xp goal.",
			section = rangedSkill
	)
	default int rangedXpGoal()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "rangedVisibilityPatterns",
			name = "Visibility Patterns",
			description = "Configures visibility patterns.",
			section = rangedSkill
	)
	default String rangedPattens()
	{
		return "";
	}
}
