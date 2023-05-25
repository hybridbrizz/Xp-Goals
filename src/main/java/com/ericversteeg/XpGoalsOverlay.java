package com.ericversteeg;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class XpGoalsOverlay extends Overlay {

	private final int ICON_SIZE = 16;

	private final Client client;
	private final XpGoalsPlugin plugin;
	private final XpGoalsConfig config;
	private final SkillIconManager iconManager;

	private Font font;

	@Inject
	private XpGoalsOverlay(
			Client client,
			XpGoalsPlugin plugin,
			XpGoalsConfig config,
			SkillIconManager iconManager
	) {
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);

		this.client = client;
		this.plugin = plugin;
		this.config = config;

		this.iconManager = iconManager;

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			InputStream inRunescapeSmall = FontManager.class.getResourceAsStream("runescape_small.ttf");
			Font smallFont = Font.createFont(Font.TRUETYPE_FONT, inRunescapeSmall)
					.deriveFont(Font.PLAIN, 12);
			ge.registerFont(smallFont);
			font = smallFont;
		}
		catch (Exception e)
		{
			font = FontManager.getRunescapeSmallFont();
		}
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		List<Goal> goals = getTrackedGoals();

		if (!goals.isEmpty())
		{
			int bgX = config.anchorX() - 10;
			int bgY = config.anchorY() - 10;
			int bgW = ICON_SIZE + 5 + config.barWidth() + 20;
			int bgH = ICON_SIZE * goals.size() + config.barVerticalSpacing() * (goals.size() - 1) + 20;

			renderBackground(graphics, bgX, bgY, bgW, bgH);
		}

		int offsetY = 0;
		for (int i = 0; i < goals.size(); i++)
		{
			Goal goal = goals.get(i);
			if (goal.enabled)
			{
				float progress = getXpProgress(
						goal.startXp, goal.currentXp,
						goal.goalXp + goal.startXp);

				renderSkillIcon(graphics, offsetY, goal);

				renderXpBar(graphics, offsetY, progress);

				renderProgressText(graphics, offsetY, progress);

				offsetY += config.barVerticalSpacing();
			}
		}

		return null;
	}

	private void renderBackground(Graphics2D graphics, int x, int y, int width, int height)
	{
		graphics.setColor(config.bgColor());
		graphics.fillRect(x, y, width, height);

		graphics.setColor(config.bgOuterBorder());
		graphics.drawRect(x, y, width, height);

		graphics.setColor(config.bgInnerBorder());
		graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
	}

	private void renderSkillIcon(Graphics2D graphics2D, int offsetY, Goal goal)
	{
		Skill skill = getSkillForId(goal.skillId);
		if (skill == null) return;

		BufferedImage icon = iconManager.getSkillImage(skill);
		icon = ImageUtil.resizeImage(icon, ICON_SIZE, ICON_SIZE, true);

//		graphics2D.setColor(Color.decode("#99000000"));
//		graphics2D.fillRoundRect(config.anchorX() - 2, config.anchorY() - 2,
//				icon.getWidth() + 4, icon.getHeight() + 4,
//				(icon.getWidth() + 4) / 2, (icon.getHeight() + 4) / 2);
		graphics2D.drawImage(icon, config.anchorX(), config.anchorY() + offsetY, null);
	}

	private void renderXpBar(Graphics2D graphics, int offsetY, float progress)
	{
		Color backColor = Color.DARK_GRAY;
		Color frontColor = Color.decode("#30FCAB");
		float relPercent = progress;

		if (progress > 1)
		{
			backColor = Color.decode("#30FCAB");
			frontColor = Color.decode("#A020F0");
			relPercent = progress - 1;

			if (relPercent > 1)
			{
				relPercent = 1;
			}
		}

//		if (progress < 0.25)
//		{
//			backColor = Color.DARK_GRAY;
//			frontColor = Color.RED;
//			relPercent = progress / 0.25f;
//		}
//		else if (progress < 0.50)
//		{
//			backColor = Color.RED;
//			frontColor = Color.ORANGE;
//			relPercent = (progress - 0.25f) / 0.25f;
//		}
//		else if (progress < 0.75)
//		{
//			backColor = Color.ORANGE;
//			frontColor = Color.YELLOW;
//			relPercent = (progress - 0.5f) / 0.25f;
//		}
//		else if (progress < 1)
//		{
//			backColor = Color.YELLOW;
//			frontColor = Color.GREEN;
//			relPercent = (progress - 0.75f) / 0.25f;
//		}
//		else if (progress < 1.25)
//		{
//			backColor = Color.GREEN;
//			frontColor = Color.BLUE;
//			relPercent = (progress - 1f) / 0.25f;
//		}
//		else if (progress <= 1.50)
//		{
//			backColor = Color.BLUE;
//			frontColor = Color.decode("#A020F0");
//			relPercent = (progress - 1.25f) / 0.25f;
//		}
//		else
//		{
//			backColor = Color.BLUE;
//			frontColor = Color.decode("#A020F0");
//			relPercent = 1f;
//		}

		int w = config.barWidth();
		int h = config.barHeight();

		int x = config.anchorX() + ICON_SIZE + 5;
		int y = config.anchorY() + offsetY + ICON_SIZE / 2 - h / 2;

		graphics.setColor(backColor);
		graphics.fillRect(x, y, w, h);

		graphics.setColor(frontColor);
		graphics.fillRect(x, y, (int) (relPercent * w), h);

		graphics.setColor(Color.decode("#0b0b0b"));
		graphics.drawRect(x, y, w, h);
	}

	private void renderProgressText(Graphics2D graphics, int offsetY, float progress)
	{
		TextComponent textComponent = new TextComponent();
		graphics.setFont(font);
		FontMetrics fontMetrics = graphics.getFontMetrics();

		int xMulti = 1;
		if (config.textOffsetXNegative())
		{
			xMulti = -1;
		}

		int yMulti = 1;
		if (config.textOffsetYNegative())
		{
			yMulti = -1;
		}

		String text = String.format("%.3f", progress * 100) + "%";
		int x = config.anchorX() + ICON_SIZE + 5 + config.barWidth() - fontMetrics.stringWidth(text) + config.textOffsetX() * xMulti;
		int y = config.anchorY() + offsetY + ICON_SIZE / 2 - 3 + config.textOffsetY() * yMulti;

		textComponent.setText(text);
		textComponent.setPosition(new Point(x, y));
		textComponent.setColor(Color.WHITE);
		textComponent.render(graphics);
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
		return tracked.stream().sorted((obj, other) ->
				(int) (getXpProgress(
						other.startXp,
						other.currentXp,
						other.startXp + other.goalXp
				) * 100) -
				(int) (getXpProgress(
						obj.startXp,
						obj.currentXp,
						obj.startXp + obj.goalXp
				) * 100)
		).collect(Collectors.toList());
	}

	Skill getSkillForId(int skillId)
	{
		if (skillId == Skill.ATTACK.ordinal()) return Skill.ATTACK;
		else if (skillId == Skill.STRENGTH.ordinal()) return Skill.STRENGTH;
		else if (skillId == Skill.DEFENCE.ordinal()) return Skill.DEFENCE;
		else if (skillId == Skill.RANGED.ordinal()) return Skill.RANGED;
		else if (skillId == Skill.PRAYER.ordinal() )return Skill.PRAYER;
		else if (skillId == Skill.MAGIC.ordinal()) return Skill.MAGIC;
		else if (skillId == Skill.RUNECRAFT.ordinal()) return Skill.RUNECRAFT;
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return Skill.CONSTRUCTION;
		else if (skillId == Skill.HITPOINTS.ordinal()) return Skill.HITPOINTS;
		else if (skillId == Skill.AGILITY.ordinal()) return Skill.AGILITY;
		else if (skillId == Skill.HERBLORE.ordinal()) return Skill.HERBLORE;
		else if (skillId == Skill.THIEVING.ordinal()) return Skill.THIEVING;
		else if (skillId == Skill.CRAFTING.ordinal()) return Skill.CRAFTING;
		else if (skillId == Skill.FLETCHING.ordinal()) return Skill.FLETCHING;
		else if (skillId == Skill.SLAYER.ordinal()) return Skill.SLAYER;
		else if (skillId == Skill.HUNTER.ordinal()) return Skill.HUNTER;
		else if (skillId == Skill.MINING.ordinal()) return Skill.MINING;
		else if (skillId == Skill.SMITHING.ordinal()) return Skill.SMITHING;
		else if (skillId == Skill.FISHING.ordinal()) return Skill.FISHING;
		else if (skillId == Skill.COOKING.ordinal()) return Skill.COOKING;
		else if (skillId == Skill.FIREMAKING.ordinal()) return Skill.FIREMAKING;
		else if (skillId == Skill.WOODCUTTING.ordinal()) return Skill.WOODCUTTING;
		else if (skillId == Skill.FARMING.ordinal()) return Skill.FARMING;
		else return null;
	}
}
