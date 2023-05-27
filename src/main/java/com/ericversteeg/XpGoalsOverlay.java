package com.ericversteeg;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

class XpGoalsOverlay extends Overlay {

	private final int ICON_SIZE = 16;
	private final int VERTICAL_SPACING = 28;
	private final int BAR_HEIGHT = 6;

	private final Client client;
	private final XpGoalsPlugin plugin;
	private final XpGoalsConfig config;
	private final SkillIconManager iconManager;

	private Font font;

	private Color outerBorderColor = new Color(57, 41, 13, 124);
	private Color innerBorderColor = new Color(147, 141, 130, 37);
	private Color pastProgressBgColor = new Color(30, 30, 30, 125);

	int panelTopPadding = 7;
	int panelBottomPadding = 7;
	int panelHPadding = 4;

	private int panelX;
	private int panelY;
	private int panelWidth;
	private int panelHeight;

	private int titleHeight;

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
					.deriveFont(Font.PLAIN,  12);
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

		net.runelite.api.Point mouse = client.getMouseCanvasPosition();
		int mouseX = mouse.getX();
		int mouseY = mouse.getY();

		boolean hideLabel = config.hideLabel();
		boolean hideText = config.hideText();

		int barWidth = Math.min(config.barWidth(), 1000);

		if (!goals.isEmpty())
		{
			if (hideLabel)
			{
				titleHeight = 0;
			}
			else
			{
				titleHeight = 20;
			}

			if (hideText)
			{
				panelTopPadding = 5;
			}
			else
			{
				panelTopPadding = 7;
			}

			panelX = config.anchorX() - panelHPadding;
			panelY = config.anchorY() - panelTopPadding;
			panelWidth = ICON_SIZE + 5 + barWidth + panelHPadding * 2;
			panelHeight =  titleHeight + VERTICAL_SPACING *
					goals.size() + panelTopPadding + panelBottomPadding - ICON_SIZE + 1;

			renderPanel(graphics, panelX, panelY, panelWidth, panelHeight);

			if (!hideLabel)
			{
				renderLabel(graphics);
			}
		}

		Goal tooltipGoal = null;

		int offsetY = titleHeight;
		for (int i = 0; i < goals.size(); i++)
		{
			Goal goal = goals.get(i);
			if (goal.enabled)
			{
				float progress = getXpProgress(
						goal.startXp, goal.currentXp,
						goal.goalXp + goal.startXp);

				renderSkillIcon(graphics, offsetY, goal);

				renderXpBar(
						graphics,
						barWidth,
						offsetY,
						progress,
						progressColor(goal.skillId)
				);

				int remainingXp = Math.max(goal.startXp + goal.goalXp - goal.currentXp, 0);
				renderProgressText(graphics, barWidth, offsetY, progress, remainingXp);

				if (progress >= 1)
				{
					//renderDoneImage(graphics, offsetY, textWidth);
				}

				Rectangle2D rectangle = new Rectangle2D.Float(
						panelX,
						panelY + offsetY,
						panelWidth,
						VERTICAL_SPACING
				);

				if (rectangle.contains(mouseX, mouseY))
				{
					tooltipGoal = goal;
				}

				offsetY += VERTICAL_SPACING;
			}
		}

		if (tooltipGoal != null)
		{
			renderPastProgressTooltip(graphics, mouseX + 5, mouseY + 5, tooltipGoal);
		}

		return null;
	}

	private void renderPanel(Graphics2D graphics, int x, int y, int width, int height)
	{
		graphics.setColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
		graphics.fillRect(x, y, width, height);

		graphics.setColor(outerBorderColor);
		graphics.drawRect(x, y, width, height);

		graphics.setColor(outerBorderColor);
		graphics.drawRect(x - 1, y - 1, width + 2, height + 2);

		graphics.setColor(innerBorderColor);
		graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
	}

	private void renderLabel(Graphics2D graphics)
	{
		String label = config.labelText();
		FontMetrics fontMetrics = graphics.getFontMetrics();

		TextComponent textComponent = new TextComponent();
		textComponent.setFont(FontManager.getRunescapeFont());
		textComponent.setText(label);
		textComponent.setColor(Color.GREEN);
		textComponent.setPosition(new Point(panelWidth / 2 - fontMetrics.stringWidth(label) / 2 + panelX, panelY + 18));
		textComponent.render(graphics);
	}

	private void renderSkillIcon(Graphics2D graphics2D, int offsetY, Goal goal)
	{
		Skill skill = getSkillForId(goal.skillId);
		if (skill == null) return;

		BufferedImage icon = iconManager.getSkillImage(skill);
		icon = ImageUtil.resizeImage(icon, ICON_SIZE, ICON_SIZE, true);

		graphics2D.drawImage(icon, config.anchorX(), config.anchorY() + offsetY, null);
	}

	private void renderXpBar(Graphics2D graphics, int barWidth, int offsetY, float progress, Color progressColor)
	{
		Color backColor = Color.DARK_GRAY;
		Color frontColor = progressColor;
		float relPercent = progress;

		if (progress > 1)
		{
			backColor = progressColor;
			frontColor = Color.decode("#A020F0");
			relPercent = progress - 1;

			if (relPercent > 1)
			{
				relPercent = 1;
			}
		}

		int h = BAR_HEIGHT;

		int x = config.anchorX() + ICON_SIZE + 5;
		int y = config.anchorY() + offsetY + ICON_SIZE / 2 - h / 2;

		graphics.setColor(backColor);
		graphics.fillRect(x, y, barWidth, h);

		graphics.setColor(frontColor);
		graphics.fillRect(x, y, (int) (relPercent * barWidth), h);

		graphics.setColor(Color.decode("#0b0b0b"));
		graphics.drawRect(x, y, barWidth, h);
	}

	private int renderProgressText(Graphics2D graphics, int barWidth, int offsetY, float progress, int remainingXp)
	{
		if (config.hideText()) return 0;

		TextComponent textComponent = new TextComponent();
		graphics.setFont(font);
		FontMetrics fontMetrics = graphics.getFontMetrics();

		String textFormatStr = "%." + Math.min(config.textPrecision(), 20) + "f";

		String text = String.format(textFormatStr, progress * 100) + "%";
		if (config.showRemainingXp())
		{
			text = NumberFormat.getInstance(Locale.ENGLISH).format(remainingXp);
		}

		int w = fontMetrics.stringWidth(text);

		int x = config.anchorX() + ICON_SIZE + 5 + barWidth - w;
		int y = config.anchorY() + offsetY + ICON_SIZE / 2 - 4;

		textComponent.setText(text);
		textComponent.setPosition(new Point(x, y));
		textComponent.setColor(Color.WHITE);
		textComponent.render(graphics);

		return w;
	}

	private void renderPastProgressTooltip(Graphics2D graphics, int x, int y, Goal goal)
	{
		int w = 120;
		int h = 120;

		int border = 2;

		int span = Math.min(config.pastProgressSpan(), 5);
		span = Math.max(span, 1);

		if (span > 4)
		{
			w += 20;
			h += 20;
		}

		w -= (w - border * 2) % span;
		h -= (h - border * 2) % span;

		renderPanel(graphics, x, y, w, h);

		int xx = x + border;
		int yy = y + border;
		int ww = w - border * 2;
		int hh = h - border * 2;

		int cW = ww / span;
		int rH = hh / span;

		List<Float> pastProgress = goal.pastProgress;

		for (int r = 0; r < span; r++)
		{
			for (int c = 0; c < span; c++)
			{
				if (r * span + c < pastProgress.size())
				{
					float progress = pastProgress.get(pastProgress.size() - 1 - (r * span + c));

					renderPastProgressItem(
							graphics,
							xx + cW * c,
							yy + rH * r,
							cW,
							rH,
							goal,
							progress
					);
				}
			}
		}
	}

	private void renderPastProgressItem(Graphics2D graphics, int x, int y, int w, int h, Goal goal, float progress)
	{
		Color color = progressColor(goal.skillId);
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 125));

		//System.out.println("x = " + x + ", y = " + y + ", w = " + w + ", h = " + h + "yy = " + y + (h - (int) (h * progress)) + ", hh = " + (int) (h * progress));

		float percentToFill = Math.min(progress, 1);

		graphics.fillRect(x, y + (h - (int) (h * percentToFill)), w, (int) (h * percentToFill));

		graphics.setFont(FontManager.getRunescapeSmallFont());
		FontMetrics fontMetrics = graphics.getFontMetrics();

		String text = (int) (progress * 100) + "%";

		TextComponent textComponent = new TextComponent();
		textComponent.setFont(FontManager.getRunescapeSmallFont());
		textComponent.setText(text);
		textComponent.setPosition(new Point(x + (w - fontMetrics.stringWidth(text)) / 2, y + (h - fontMetrics.getHeight()) / 2 + 13));
		if (progress >= 1)
		{
			textComponent.setColor(Color.GREEN);
		}
		else
		{
			textComponent.setColor(Color.RED);
		}
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

	Color progressColor(int skillId)
	{
		if (skillId == Skill.MINING.ordinal()) return config.miningProgressColor();
		else if (skillId == Skill.RUNECRAFT.ordinal()) return config.runecraftingProgressColor();
		else if (skillId == Skill.AGILITY.ordinal()) return config.agilityProgressColor();
		else if (skillId == Skill.FISHING.ordinal()) return config.fishingProgressColor();
		else if (skillId == Skill.WOODCUTTING.ordinal()) return config.woodcuttingProgressColor();
		else if (skillId == Skill.FARMING.ordinal()) return config.farmingProgressColor();
		else if (skillId == Skill.RANGED.ordinal()) return config.rangedProgressColor();
		else if (skillId == Skill.SLAYER.ordinal()) return config.slayerProgressColor();
		else if (skillId == Skill.ATTACK.ordinal()) return config.attackProgressColor();
		else if (skillId == Skill.DEFENCE.ordinal()) return config.defenseProgressColor();
		else if (skillId == Skill.STRENGTH.ordinal()) return config.strengthProgressColor();
		else if (skillId == Skill.MAGIC.ordinal()) return config.magicProgressColor();
		else if (skillId == Skill.PRAYER.ordinal()) return config.prayerProgressColor();
		else if (skillId == Skill.CONSTRUCTION.ordinal()) return config.constructionProgressColor();
		else if (skillId == Skill.HITPOINTS.ordinal()) return config.hitpointsProgressColor();
		else if (skillId == Skill.HERBLORE.ordinal()) return config.herbloreProgressColor();
		else if (skillId == Skill.THIEVING.ordinal()) return config.thievingProgressColor();
		else if (skillId == Skill.CRAFTING.ordinal()) return config.craftingProgressColor();
		else if (skillId == Skill.FLETCHING.ordinal()) return config.fletchingProgressColor();
		else if (skillId == Skill.HUNTER.ordinal()) return config.hunterProgressColor();
		else if (skillId == Skill.SMITHING.ordinal()) return config.smithingProgressColor();
		else if (skillId == Skill.COOKING.ordinal()) return config.cookingProgressColor();
		else if (skillId == Skill.FIREMAKING.ordinal()) return config.firemakingProgressColor();
		else return Color.decode("#30FCAB");
	}
}
