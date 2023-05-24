package com.ericversteeg;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

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
}
