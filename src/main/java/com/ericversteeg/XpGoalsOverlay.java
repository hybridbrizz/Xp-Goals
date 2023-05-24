package com.ericversteeg;

import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class XpGoalsOverlay extends Overlay {

	private final Client client;
	private final XpGoalsPlugin plugin;
	private final XpGoalsConfig config;
	private final ItemManager itemManager;

	@Inject
	private XpGoalsOverlay(
			Client client,
			XpGoalsPlugin plugin,
			XpGoalsConfig config,
			ItemManager itemManager
	) {
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);

		this.client = client;
		this.plugin = plugin;
		this.config = config;

		this.itemManager = itemManager;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		List<Goal> goals = getTrackedGoals();
		int offsetY = 0;
		for (int i = 0; i < goals.size(); i++)
		{
			Goal goal = goals.get(i);
			if (goal.enabled)
			{
				renderXpBar(graphics, offsetY, getXpProgress(
						goal.startXp, goal.currentXp,
						goal.goalXp + goal.startXp));

				offsetY += 30;
			}
		}

		return null;
	}

	private void renderXpBar(Graphics2D graphics, int offsetY, float progress)
	{
		Color backColor;
		Color frontColor;
		float relPercent;

		if (progress < 0.25)
		{
			backColor = Color.DARK_GRAY;
			frontColor = Color.RED;
			relPercent = progress / 0.25f;
		}
		else if (progress < 0.50)
		{
			backColor = Color.RED;
			frontColor = Color.ORANGE;
			relPercent = (progress - 0.25f) / 0.25f;
		}
		else if (progress < 0.75)
		{
			backColor = Color.ORANGE;
			frontColor = Color.YELLOW;
			relPercent = (progress - 0.5f) / 0.25f;
		}
		else if (progress < 1)
		{
			backColor = Color.YELLOW;
			frontColor = Color.GREEN;
			relPercent = (progress - 0.75f) / 0.25f;
		}
		else if (progress < 1.25)
		{
			backColor = Color.GREEN;
			frontColor = Color.BLUE;
			relPercent = (progress - 1f) / 0.25f;
		}
		else if (progress <= 1.50)
		{
			backColor = Color.BLUE;
			frontColor = Color.decode("#A020F0");
			relPercent = (progress - 1.25f) / 0.25f;
		}
		else
		{
			backColor = Color.BLUE;
			frontColor = Color.decode("#A020F0");
			relPercent = 1f;
		}

		graphics.setColor(backColor);
		graphics.fillRect(config.anchorX(), config.anchorY() + offsetY, 80, 5);

		graphics.setColor(frontColor);
		graphics.fillRect(config.anchorX(), config.anchorY() + offsetY, (int) (relPercent * 80), 5);
	}

	private float getXpProgress(int startXp, int currentXp, int goalXp)
	{
		if (startXp < 0) return 0f;
		else if (goalXp <= startXp) return 0f;
		return (currentXp - startXp) * 1f / (goalXp - startXp);
	}

	List<Goal> getTrackedGoals()
	{
		List<Goal> tracked = new LinkedList<>();

		for (Goal goal: plugin.goalData.goals)
		{
			if (goal.track)
			{
				tracked.add(goal);
			}
		}
		return tracked;
	}
}
