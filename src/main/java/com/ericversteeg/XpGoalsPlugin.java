package com.ericversteeg;

import com.ericversteeg.pattern.Pattern;
import com.google.gson.Gson;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.RuneScapeProfileChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@PluginDescriptor(
	name = "Xp Goalz",
	description = "Track xp goals."
)

public class XpGoalsPlugin extends Plugin
{
	@Inject private XpGoalsOverlay overlay;
	@Inject private OverlayManager overlayManager;
	@Inject private Client client;
	@Inject private XpGoalsConfig config;
	@Inject private ConfigManager configManager;
	@Inject private Gson gson;

	GoalData goalData;

	private String profileKey = "";

	ZoneOffset zoneOffset = ZoneId.systemDefault()
		.getRules()
		.getOffset(
			LocalDate.now()
				.atStartOfDay()
		);
	LocalDateTime lastDateTime = null;

	// so the first hit after a reset is tracked
	private Map<Integer, Integer> skillsXp = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		writeSavedData();
	}

	@Subscribe
	public void onRuneScapeProfileChanged(RuneScapeProfileChanged e)
	{
		profileKey = configManager.getRSProfileKey();
		if (profileKey != null)
		{
			goalData = getSavedData();

			lastDateTime = LocalDateTime.ofEpochSecond(
				goalData.lastCheck,
				0,
				zoneOffset
			);

			checkResets();
		}
	}

	@Provides
	XpGoalsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(XpGoalsConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged config)
	{
		configSyncGoals();
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		checkResets();
	}

	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
		Skill skill = statChanged.getSkill();
		int xp = statChanged.getXp();

		Goal goal = goalForSkillId(skill.ordinal());

		if (goal != null && goal.track)
		{
			int lastXp = xp;
			if (skillsXp.containsKey(skill.ordinal()))
			{
				lastXp = skillsXp.get(skill.ordinal());
			}

			if (goal.startXp < 0) { goal.startXp = lastXp; }
			goal.currentXp = xp;
		}

		skillsXp.put(skill.ordinal(), xp);
	}

	private void checkResets()
	{
		if (lastDateTime == null) return;

		LocalDateTime dateTIme = LocalDateTime.now();

		boolean reset = false;

		if (dateTIme.get(ChronoField.HOUR_OF_DAY) != lastDateTime.get(ChronoField.HOUR_OF_DAY)
				|| (dateTIme.get(ChronoField.DAY_OF_MONTH) != lastDateTime.get(ChronoField.DAY_OF_MONTH))
				|| (dateTIme.get(ChronoField.MONTH_OF_YEAR) != lastDateTime.get(ChronoField.MONTH_OF_YEAR))
				|| (dateTIme.get(ChronoField.YEAR) != lastDateTime.get(ChronoField.YEAR)))
		{
			resetGoals(Goal.resetHourly);
			configSyncGoals();
			reset = true;
		}
		if (dateTIme.get(ChronoField.DAY_OF_MONTH) != lastDateTime.get(ChronoField.DAY_OF_MONTH)
				|| (dateTIme.get(ChronoField.MONTH_OF_YEAR) != lastDateTime.get(ChronoField.MONTH_OF_YEAR))
				|| (dateTIme.get(ChronoField.YEAR) != lastDateTime.get(ChronoField.YEAR)))
		{
			resetGoals(Goal.resetDaily);
			reset = true;
		}
		if (dateTIme.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) != lastDateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
				|| (dateTIme.get(ChronoField.YEAR) != lastDateTime.get(ChronoField.YEAR)))
		{
			resetGoals(Goal.resetWeekly);
			reset = true;
		}
		if (dateTIme.get(ChronoField.MONTH_OF_YEAR) != lastDateTime.get(ChronoField.MONTH_OF_YEAR)
				|| (dateTIme.get(ChronoField.YEAR) != lastDateTime.get(ChronoField.YEAR)))
		{
			resetGoals(Goal.resetMonthly);
			reset = true;
		}
		if (dateTIme.get(ChronoField.YEAR) != lastDateTime.get(ChronoField.YEAR))
		{
			resetGoals(Goal.resetYearly);
			reset = true;
		}

		// check save data
		if (dateTIme.get(ChronoField.MINUTE_OF_HOUR) != lastDateTime.get(ChronoField.MINUTE_OF_HOUR) || reset)
		{
			writeSavedData();
		}

		goalData.lastCheck = dateTIme.toEpochSecond(zoneOffset);
		lastDateTime = dateTIme;
	}

	private void resetGoals(int resetType)
	{
		for (Goal goal: goalData.goals)
		{
			if (goal.resetType == resetType)
			{
				goal.reset();
			}
		}
	}

	GoalData getSavedData()
	{
		String profile = configManager.getRSProfileKey();
		String json = configManager.getConfiguration(XpGoalsConfig.GROUP, profile, "xp_goals_data");

		GoalData savedData = gson.fromJson(json, GoalData.class);

		if (savedData == null)
		{
			return new GoalData();
		}
		return savedData;
	}

	void writeSavedData()
	{
		String profile = configManager.getRSProfileKey();

		String json = gson.toJson(goalData);
		configManager.setConfiguration(XpGoalsConfig.GROUP, profile, "xp_goals_data", json);
	}

	void configSyncGoals()
	{
		if (goalData == null) return;

		if (goalData.goals.isEmpty())
		{
			goalData.goals = Arrays.asList(
					new Goal(Skill.ATTACK.ordinal()),
					new Goal(Skill.STRENGTH.ordinal()),
					new Goal(Skill.DEFENCE.ordinal()),
					new Goal(Skill.RANGED.ordinal()),
					new Goal(Skill.PRAYER.ordinal()),
					new Goal(Skill.MAGIC.ordinal()),
					new Goal(Skill.RUNECRAFT.ordinal()),
					new Goal(Skill.CONSTRUCTION.ordinal()),
					new Goal(Skill.HITPOINTS.ordinal()),
					new Goal(Skill.AGILITY.ordinal()),
					new Goal(Skill.HERBLORE.ordinal()),
					new Goal(Skill.THIEVING.ordinal()),
					new Goal(Skill.CRAFTING.ordinal()),
					new Goal(Skill.FLETCHING.ordinal()),
					new Goal(Skill.SLAYER.ordinal()),
					new Goal(Skill.HUNTER.ordinal()),
					new Goal(Skill.MINING.ordinal()),
					new Goal(Skill.SMITHING.ordinal()),
					new Goal(Skill.FISHING.ordinal()),
					new Goal(Skill.COOKING.ordinal()),
					new Goal(Skill.FIREMAKING.ordinal()),
					new Goal(Skill.WOODCUTTING.ordinal()),
					new Goal(Skill.FARMING.ordinal())
			);
		}

		for (Goal goal: goalData.goals)
		{
			int skillId = goal.skillId;

			boolean enabled = isGoalEnabled(skillId);
			boolean track = enabled;
			int goalXp = goalXp(skillId);
			ResetType resetType = goalResetType(skillId);

			boolean oneValidPattern = false;
			boolean patternError = false;

			if (enabled) {
				track = false;

				try {
					String [] patterns = goalPatterns(skillId).split("\n");
					for (String patternStr: patterns)
					{
						if (patternStr.trim().isEmpty()) continue;

						oneValidPattern = true;

						String pattern = "";

						if (patternStr.contains("="))
						{
							String [] patternParts = patternStr.split("=");
							pattern = patternParts[0].trim();

							goalXp = Integer.parseInt(patternParts[1].trim());
						}
						else
						{
							pattern = patternStr;
						}

						if (Pattern.parse(pattern, new Date()).matches())
						{
							track = true;
							break;
						}
					}
				}
				catch (Exception exception) {
					patternError = true;
				}
			}

			if (!oneValidPattern || patternError) {
				track = enabled;
			}

//			if (goalXp <= 0)
//			{
//				track = false;
//			}

			goal.resetType = resetType.ordinal();
			goal.enabled = enabled;
			goal.track = track;
			goal.goalXp = goalXp;

			if (!enabled)
			{
				goal.startXp = -1;
				goal.currentXp = -1;
			}
		}
	}

	Goal goalForSkillId(int skillId)
	{
		for (Goal goal: goalData.goals)
		{
			if (goal.skillId == skillId)
			{
				return goal;
			}
		}
		return null;
	}

	boolean isGoalEnabled(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return config.enableMiningSkill();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.enableRunecraftingSkill();
		else if (skillId == Skill.AGILITY.ordinal()) return config.enableAgilitySkill();
		else if (skillId == Skill.FISHING.ordinal()) return config.enableFishingSkill();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.enableWoodcuttingSkill();
		else if (skillId == Skill.FARMING.ordinal()) return config.enableFarmingSkill();
		else if (skillId == Skill.RANGED.ordinal()) return config.enableRangedSkill();
		else if (skillId == Skill.SLAYER.ordinal()) return config.enableSlayerSkill();
		else if (skillId == Skill.ATTACK.ordinal()) return config.enableAttackSkill();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.enableDefenseSkill();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.enableStrengthSkill();
		else if (skillId == Skill.MAGIC.ordinal()) return config.enableMagicSkill();
		else if (skillId == Skill.PRAYER.ordinal()) return config.enablePrayerSkill();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.enableConstructionSkill();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.enableHitpointsSkill();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.enableHerbloreSkill();
		else if (skillId == Skill.THIEVING.ordinal()) return config.enableThievingSkill();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.enableCraftingSkill();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.enableFletchingSkill();
		else if (skillId == Skill.HUNTER.ordinal()) return config.enableHunterSkill();
		else if (skillId == Skill.SMITHING.ordinal()) return config.enableSmithingSkill();
		else if (skillId == Skill.COOKING.ordinal()) return config.enableCookingSkill();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.enableFiremakingSkill();
		else return false;
	}

	ResetType goalResetType(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return config.miningResetType();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingResetType();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityResetType();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingResetType();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingResetType();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingResetType();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedResetType();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerResetType();
		else if (skillId == Skill.ATTACK.ordinal()) return config.attackResetType();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseResetType();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthResetType();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicResetType();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerResetType();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionResetType();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsResetType();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreResetType();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingResetType();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingResetType();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingResetType();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterResetType();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingResetType();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingResetType();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingResetType();
		else return ResetType.NONE;
	}

	int goalXp(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return config.miningXpGoal();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingXpGoal();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityXpGoal();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingXpGoal();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingXpGoal();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingXpGoal();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedXpGoal();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerXpGoal();
		else if (skillId == Skill.ATTACK.ordinal()) return config.attackXpGoal();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseXpGoal();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthXpGoal();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicXpGoal();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerXpGoal();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionXpGoal();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsXpGoal();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreXpGoal();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingXpGoal();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingXpGoal();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingXpGoal();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterXpGoal();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingXpGoal();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingXpGoal();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingXpGoal();
		else return 0;
	}

	String goalPatterns(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return config.miningPattens();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingPattens();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityPattens();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingPattens();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingPattens();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingPattens();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedPattens();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerPattens();
		else if (skillId == Skill.ATTACK.ordinal()) return config.attackPattens();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defensePattens();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthPattens();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicPattens();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerPattens();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionPattens();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsPattens();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herblorePattens();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingPattens();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingPattens();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingPattens();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterPattens();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingPattens();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingPattens();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingPattens();
		else return "";
	}
}
