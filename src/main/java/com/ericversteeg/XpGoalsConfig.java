package com.ericversteeg;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(XpGoalsConfig.GROUP)
public interface XpGoalsConfig extends Config
{
	String GROUP = "xpgoals";

	@ConfigItem(
			position = 0,
			keyName = "enableProfitLoss",
			name = "Profit / Loss",
			description = "Configures whether or not current total is relative to start amount."
	)
	default boolean enableProfitLoss()
	{
		return false;
	}
}
