package com.ericversteeg;

import com.ericversteeg.config.DayCadence;
import com.ericversteeg.config.Hour;
import com.ericversteeg.goal.Goal;
import com.ericversteeg.goal.GoalData;
import com.ericversteeg.goal.ResetType;
import com.ericversteeg.pattern.Pattern;
import com.google.gson.Gson;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.RuneScapeProfileChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.*;

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

	private static String DATA_KEY = "xp_goals_data_3";

	GoalData goalData;

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
		String profile = configManager.getRSProfileKey();
		if (profile != null)
		{
			goalData = getSavedData();

			lastDateTime = LocalDateTime.ofEpochSecond(
				goalData.lastCheck,
				0,
				zoneOffset
			);

			checkResets();
		}
		else
		{
			goalData = new GoalData();
			configSyncGoals();
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

			if (goal.progressXp < 0) goal.progressXp = 0;
			goal.progressXp += xp - lastXp;
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
		String json = configManager.getConfiguration(XpGoalsConfig.GROUP, profile, DATA_KEY);

		if (json == null)
		{
			return new GoalData();
		}

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

		if (profile != null)
		{
			String json = gson.toJson(goalData);
			configManager.setConfiguration(XpGoalsConfig.GROUP, profile, DATA_KEY, json);
		}
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
					String [] patterns;
					List<String> patternsList = new LinkedList<>();

					String dayOfWeekPatternPart = dayOfWeekPatternPart(skillId);
					String dayCadencePatternPart = dayCadencePatternPart(skillId);
					String hourPatternPart = hourPatternPart(skillId);

					String customPatterns = customPatterns(skillId);

					if (!customPatterns.trim().isEmpty())
					{
						patterns = customPatterns.split("\n");
					}
					else
					{
						if (dayOfWeekPatternPart != null)
						{
							if (hourPatternPart != null)
							{
								patternsList.add(dayOfWeekPatternPart + "->" + hourPatternPart);
							}
							else
							{
								patternsList.add(dayOfWeekPatternPart);
							}
						}
						if (dayCadencePatternPart != null)
						{
							if (hourPatternPart != null)
							{
								patternsList.add(dayCadencePatternPart + "->" + hourPatternPart);
							}
							else
							{
								patternsList.add(dayCadencePatternPart);
							}
						}
						if (dayOfWeekPatternPart == null && dayCadencePatternPart == null)
						{
							if (hourPatternPart != null)
							{
								patternsList.add(hourPatternPart);
							}
						}

						patterns = patternsList.toArray(new String[0]);
					}

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

			goal.resetType = resetType.ordinal();
			goal.enabled = enabled;
			goal.track = track;
			goal.goalXp = goalXp;
		}
	}

	Skill skillForSkillId(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return Skill.MINING;
		else if (skillId == Skill.RUNECRAFT.ordinal()) return Skill.RUNECRAFT;
		else if (skillId == Skill.AGILITY.ordinal()) return Skill.AGILITY;
		else if (skillId == Skill.FISHING.ordinal()) return Skill.FISHING;
		else if (skillId == Skill.WOODCUTTING.ordinal()) return Skill.WOODCUTTING;
		else if (skillId == Skill.FARMING.ordinal()) return Skill.FARMING;
		else if (skillId == Skill.RANGED.ordinal()) return Skill.RANGED;
		else if (skillId == Skill.SLAYER.ordinal()) return Skill.SLAYER;
		else if (skillId == Skill.ATTACK.ordinal()) return Skill.ATTACK;
		else if (skillId == Skill.DEFENCE.ordinal()) return Skill.DEFENCE;
		else if (skillId == Skill.STRENGTH.ordinal()) return Skill.STRENGTH;
		else if (skillId == Skill.MAGIC.ordinal()) return Skill.MAGIC;
		else if (skillId == Skill.PRAYER.ordinal()) return Skill.PRAYER;
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return Skill.CONSTRUCTION;
		else if (skillId == Skill.HITPOINTS.ordinal()) return Skill.HITPOINTS;
		else if (skillId == Skill.HERBLORE.ordinal()) return Skill.HERBLORE;
		else if (skillId == Skill.THIEVING.ordinal()) return Skill.THIEVING;
		else if (skillId == Skill.CRAFTING.ordinal()) return Skill.CRAFTING;
		else if (skillId == Skill.FLETCHING.ordinal()) return Skill.FLETCHING;
		else if (skillId == Skill.HUNTER.ordinal()) return Skill.HUNTER;
		else if (skillId == Skill.SMITHING.ordinal()) return Skill.SMITHING;
		else if (skillId == Skill.COOKING.ordinal()) return Skill.COOKING;
		else if (skillId == Skill.FIREMAKING.ordinal()) return Skill.FIREMAKING;
		else return null;
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

	String customPatterns(int skillId)
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

	boolean isMonday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackMonday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthMonday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseMonday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedMonday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerMonday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicMonday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingMonday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionMonday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsMonday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityMonday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreMonday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingMonday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingMonday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingMonday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerMonday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterMonday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningMonday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingMonday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingMonday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingMonday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingMonday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingMonday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingMonday();
		else return false;
	}

	boolean isTuesday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackTuesday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthTuesday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseTuesday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedTuesday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerTuesday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicTuesday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingTuesday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionTuesday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsTuesday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityTuesday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreTuesday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingTuesday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingTuesday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingTuesday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerTuesday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterTuesday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningTuesday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingTuesday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingTuesday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingTuesday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingTuesday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingTuesday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingTuesday();
		else return false;
	}

	boolean isWednesday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackWednesday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthWednesday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseWednesday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedWednesday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerWednesday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicWednesday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingWednesday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionWednesday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsWednesday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityWednesday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreWednesday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingWednesday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingWednesday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingWednesday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerWednesday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterWednesday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningWednesday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingWednesday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingWednesday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingWednesday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingWednesday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingWednesday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingWednesday();
		else return false;
	}

	boolean isThursday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackThursday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthThursday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseThursday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedThursday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerThursday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicThursday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingThursday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionThursday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsThursday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityThursday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreThursday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingThursday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingThursday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingThursday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerThursday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterThursday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningThursday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingThursday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingThursday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingThursday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingThursday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingThursday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingThursday();
		else return false;
	}

	boolean isFriday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackFriday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthFriday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseFriday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedFriday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerFriday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicFriday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingFriday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionFriday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsFriday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityFriday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreFriday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingFriday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingFriday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingFriday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerFriday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterFriday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningFriday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingFriday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingFriday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingFriday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingFriday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingFriday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingFriday();
		else return false;
	}

	boolean isSaturday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackSaturday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthSaturday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseSaturday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedSaturday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerSaturday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicSaturday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingSaturday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionSaturday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsSaturday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilitySaturday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreSaturday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingSaturday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingSaturday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingSaturday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerSaturday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterSaturday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningSaturday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingSaturday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingSaturday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingSaturday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingSaturday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingSaturday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingSaturday();
		else return false;
	}

	boolean isSunday(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackSunday();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthSunday();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseSunday();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedSunday();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerSunday();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicSunday();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingSunday();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionSunday();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsSunday();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilitySunday();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreSunday();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingSunday();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingSunday();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingSunday();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerSunday();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterSunday();
		else if (skillId == Skill.MINING.ordinal()) return config.miningSunday();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingSunday();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingSunday();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingSunday();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingSunday();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingSunday();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingSunday();
		else return false;
	}

	Hour startTime(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackStartTime();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthStartTime();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseStartTime();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedStartTime();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerStartTime();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicStartTime();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingStartTime();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionStartTime();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsStartTime();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityStartTime();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreStartTime();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingStartTime();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingStartTime();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingStartTime();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerStartTime();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterStartTime();
		else if (skillId == Skill.MINING.ordinal()) return config.miningStartTime();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingStartTime();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingStartTime();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingStartTime();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingStartTime();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingStartTime();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingStartTime();
		else return Hour.NONE;
	}

	Hour endTime(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackEndTime();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthEndTime();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseEndTime();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedEndTime();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerEndTime();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicEndTime();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingEndTime();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionEndTime();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsEndTime();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityEndTime();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreEndTime();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingEndTime();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingEndTime();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingEndTime();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerEndTime();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterEndTime();
		else if (skillId == Skill.MINING.ordinal()) return config.miningEndTime();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingEndTime();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingEndTime();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingEndTime();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingEndTime();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingEndTime();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingEndTime();
		else return Hour.NONE;
	}

	DayCadence dayCadence(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackDayCadence();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthDayCadence();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseDayCadence();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedDayCadence();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerDayCadence();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicDayCadence();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingDayCadence();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionDayCadence();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsDayCadence();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityDayCadence();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreDayCadence();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingDayCadence();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingDayCadence();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingDayCadence();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerDayCadence();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterDayCadence();
		else if (skillId == Skill.MINING.ordinal()) return config.miningDayCadence();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingDayCadence();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingDayCadence();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingDayCadence();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingDayCadence();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingDayCadence();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingDayCadence();
		else return DayCadence.NONE;
	}

	DayCadence dayCadenceDay(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return config.attackDayCadenceDay();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthDayCadenceDay();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseDayCadenceDay();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedDayCadenceDay();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerDayCadenceDay();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicDayCadenceDay();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingDayCadenceDay();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionDayCadenceDay();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsDayCadenceDay();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityDayCadenceDay();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreDayCadenceDay();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingDayCadenceDay();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingDayCadenceDay();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingDayCadenceDay();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerDayCadenceDay();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterDayCadenceDay();
		else if (skillId == Skill.MINING.ordinal()) return config.miningDayCadenceDay();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingDayCadenceDay();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingDayCadenceDay();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingDayCadenceDay();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingDayCadenceDay();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingDayCadenceDay();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingDayCadenceDay();
		else return DayCadence.NONE;
	}

	String dayOfWeekPatternPart(int skillId)
	{
		StringBuilder daysOfWeekSb = new StringBuilder();
		if (isMonday(skillId))
		{
			daysOfWeekSb.append("Mo,");
		}
		if (isTuesday(skillId))
		{
			daysOfWeekSb.append("Tu,");
		}
		if (isWednesday(skillId))
		{
			daysOfWeekSb.append("We,");
		}
		if (isThursday(skillId))
		{
			daysOfWeekSb.append("Th,");
		}
		if (isFriday(skillId))
		{
			daysOfWeekSb.append("Fr,");
		}
		if (isSaturday(skillId))
		{
			daysOfWeekSb.append("Sa,");
		}
		if (isSunday(skillId))
		{
			daysOfWeekSb.append("Su,");
		}

		String dayPart = null;
		if (daysOfWeekSb.length() > 0)
		{
			String daysOfWeekCsv = daysOfWeekSb.substring(0, daysOfWeekSb.length() - 1);
			dayPart = "[W]->["+ daysOfWeekCsv +"]";
		}
		return dayPart;
	}

	String dayCadencePatternPart(int skillId)
	{
		DayCadence dayCadence = dayCadence(skillId);
		DayCadence cadenceDay = dayCadenceDay(skillId);

		String dayPart = null;
		if (dayCadence != DayCadence.NONE && cadenceDay != DayCadence.NONE
				&& cadenceDay.ordinal() <= dayCadence.ordinal())
		{
			int cadence = dayCadence.ordinal();
			int offset = cadenceDay.ordinal() - 1;
			dayPart = "[2023]->[D|~"+ offset +"|" + cadence + "]";
		}
		return dayPart;
	}

	String hourPatternPart(int skillId)
	{
		Hour sHour = startTime(skillId);
		Hour eHour = endTime(skillId);

		String hourPart = null;
		if (sHour != Hour.NONE && eHour != Hour.NONE)
		{
			hourPart = "[" + sHour.getName() + "-" + eHour.getName() + "]";
		}
		return hourPart;
	}
}
