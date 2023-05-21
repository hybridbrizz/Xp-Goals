package com.ericversteeg;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(XpGoalsConfig.GROUP)
public interface XpGoalsConfig extends Config
{
	String GROUP = "xpgoals";

	@ConfigSection(
			name = "Slayer",
			description = "Slayer Goals",
			position = 0,
			closedByDefault = true
	)
	String slayerSkill = "slayerSkill";

	@ConfigItem(
			position = 0,
			keyName = "enableSlayer",
			name = "Enable Slayer",
			description = "Configures whether or not slayer skill is enabled.",
			section = slayerSkill
	)
	default boolean enableSlayerSkill()
	{
		return false;
	}

	@ConfigItem(
			position = 0,
			keyName = "slayerPatterns",
			name = "Slayer Patterns",
			description = "Configures slayer xp patterns, each pattern format is [cyclic rotation](xp). " +
					"To write patterns refer to the \"Example Skill\" section.",
			section = slayerSkill
	)
	default String slayerPattens()
	{
		return "[Sat,Sun](150000)";
	}
}
