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
	private final int MIN_BAR_SPACING = 16;

	private final Client client;
	private final XpGoalsPlugin plugin;
	private final XpGoalsConfig config;
	private final SkillIconManager iconManager;

	private Font font;

	private Color outerBorderColor = new Color(57, 41, 13, 124);
	private Color innerBorderColor = new Color(147, 141, 130, 37);
	private Color pastProgressBgColor = new Color(30, 30, 30, 125);

	int panelTopPadding = 4;
	int panelBottomPadding = 4;
	int panelHPadding = 4;

	private int panelX;
	private int panelY;
	private int panelWidth;
	private int panelHeight;

	private int topSectionHeight;

	boolean textOutsideOverride = false;
	boolean hideTextOverride = false;

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

		int barWidth = Math.max(Math.min(config.barWidth(), 1000), 45);
		int barHeight = Math.max(config.barHeight(), 2);
		int barSpacing = config.barSpacing() + MIN_BAR_SPACING;

		boolean hideLabel = config.labelText().trim().isEmpty();

		BarTextType textType = config.barTextType();
		BarTextPosition textPosition = config.barTextPosition();
		BarTextSize textSize = config.barTextSize();

		if (!goals.isEmpty())
		{
			if (hideLabel)
			{

				if (textPosition == BarTextPosition.OUTSIDE)
				{
					if (textSize == BarTextSize.SMALL)
					{
						topSectionHeight = 9;
					}
					else
					{
						topSectionHeight = 12;
					}
				}
				else
				{
					topSectionHeight = 0;
				}
			}
			else
			{
				topSectionHeight = 18;
				if ((textPosition == BarTextPosition.OUTSIDE || textOutsideOverride)
						&& textType != BarTextType.NONE && !hideTextOverride)
				{
					if (textSize == BarTextSize.SMALL)
					{
						topSectionHeight += 13;
					}
					else
					{
						topSectionHeight += 16;
					}
				}
			}

			int extraBottomPadding = 0;
			if (barHeight < ICON_SIZE)
			{
				extraBottomPadding = (ICON_SIZE - barHeight) / 2;
			}

			panelX = config.anchorX() - panelHPadding;
			panelY = config.anchorY() - panelTopPadding;
			panelWidth = ICON_SIZE + 5 + barWidth + panelHPadding * 2;
			panelHeight =  panelTopPadding + topSectionHeight + barHeight * goals.size() + barSpacing *
					Math.max(goals.size() - 1, 0) + panelBottomPadding + extraBottomPadding;

			renderPanel(graphics, panelX, panelY, panelWidth, panelHeight);

			if (!hideLabel)
			{
				renderLabel(graphics);
			}
		}

		Goal tooltipGoal = null;

		int offsetY = topSectionHeight;
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

				renderBarText(graphics, barWidth, offsetY, goal, progress);

				Rectangle2D rectangle;
				if (barHeight < ICON_SIZE)
				{
					rectangle = new Rectangle2D.Float(
							panelX,
							config.anchorY() + offsetY - (ICON_SIZE - barHeight) / 2f,
							panelWidth,
							ICON_SIZE
					);
				}
				else
				{
					rectangle = new Rectangle2D.Float(
							panelX,
							config.anchorY() + offsetY,
							panelWidth,
							barHeight
					);
				}

				if (rectangle.contains(mouseX, mouseY))
				{
					tooltipGoal = goal;
				}

				offsetY += barHeight + barSpacing;
			}
		}

		if (tooltipGoal != null)
		{
			renderPastProgressTooltip(graphics, mouseX + 15, mouseY + 15, tooltipGoal);
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
		textComponent.setPosition(new Point(
				panelWidth / 2 - fontMetrics.stringWidth(label) / 2 + panelX,
				config.anchorY() + panelTopPadding + fontMetrics.getHeight() - 2)
		);
		textComponent.render(graphics);
	}

	private void renderSkillIcon(Graphics2D graphics2D, int offsetY, Goal goal)
	{
		Skill skill = getSkillForId(goal.skillId);
		if (skill == null) return;

		BufferedImage icon = iconManager.getSkillImage(skill);
		icon = ImageUtil.resizeImage(icon, ICON_SIZE, ICON_SIZE, true);

		int barHeight = Math.max(config.barHeight(), 2);
		int y = config.anchorY() + offsetY - (ICON_SIZE - barHeight) / 2;

		graphics2D.drawImage(icon, config.anchorX(), y, null);
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

		int h = Math.max(config.barHeight(), 2);

		int x = config.anchorX() + ICON_SIZE + 5;
		int y = config.anchorY() + offsetY;

		graphics.setColor(backColor);
		graphics.fillRect(x, y, barWidth, h);

		graphics.setColor(frontColor);
		graphics.fillRect(x, y, (int) (relPercent * barWidth), h);

		graphics.setColor(Color.decode("#0b0b0b"));
		graphics.drawRect(x, y, barWidth, h);
	}

	private void renderBarText(Graphics2D graphics, int barWidth, int offsetY, Goal goal, float progress)
	{
		FontMetrics fontMetrics;
		TextComponent textComponent = new TextComponent();

		BarTextSize textSize = config.barTextSize();
		BarTextPosition position = config.barTextPosition();

		if (textSize == BarTextSize.SMALL)
		{
			graphics.setFont(font);
		}
		else
		{
			graphics.setFont(FontManager.getRunescapeSmallFont());
		}

		fontMetrics = graphics.getFontMetrics();

		int barH = Math.max(config.barHeight(), 2);

		textOutsideOverride = false;

		if (position == BarTextPosition.INSIDE)
		{
			if (barH < fontMetrics.getHeight())
			{
				graphics.setFont(font);
				fontMetrics = graphics.getFontMetrics();

				if (barH < fontMetrics.getHeight())
				{
					textOutsideOverride = true;
					if (textSize == BarTextSize.LARGE)
					{
						graphics.setFont(FontManager.getRunescapeSmallFont());
						fontMetrics = graphics.getFontMetrics();
					}
				}
			}
		}

		int h = fontMetrics.getHeight();

		String preciseFormatStr = "%.3f%%";

		Color textColor = Color.WHITE;
		String text = "";

		BarTextType textType = config.barTextType();
		switch (textType)
		{
			case FRACTION:
				text = NumberFormat.getInstance(Locale.ENGLISH).format(goal.currentXp - goal.startXp) +
						" / " + NumberFormat.getInstance(Locale.ENGLISH).format(goal.startXp + goal.goalXp - goal.startXp);
				break;
			case PERCENTAGE:
				text = (int) (progress * 100) + "%";
				break;
			case PRECISE_PERCENTAGE:
				text = String.format(preciseFormatStr, progress * 100);
				break;
			case GAINED:
				text = NumberFormat.getInstance(Locale.ENGLISH).format(goal.currentXp - goal.startXp);
				break;
			case REMAINING:
				text = NumberFormat.getInstance(Locale.ENGLISH).format(goal.startXp + goal.goalXp - goal.currentXp);
				break;
		}

		DoneTextType doneTextType = config.doneTextType();
		if (progress >= 1 && textType != BarTextType.NONE && doneTextType != DoneTextType.NONE)
		{
			text = doneTextType.getText();
		}

		int barW = Math.max(Math.min(config.barWidth(), 1000), 45);
		int w = fontMetrics.stringWidth(text);

		hideTextOverride = false;
		if (w > barW)
		{
			hideTextOverride = true;
			text = "";
		}

		int x;
		int y;

		if (position == BarTextPosition.OUTSIDE || textOutsideOverride)
		{
			x = config.anchorX() + ICON_SIZE + 5 + barWidth - w;
			y = config.anchorY() + offsetY - 1;
		}
		else
		{
			x = config.anchorX() + ICON_SIZE + 5 + (barW - w) / 2;
			y = config.anchorY() + offsetY + (barH - h) / 2 + h;
		}

		textComponent.setText(text);
		textComponent.setPosition(new Point(x, y));
		textComponent.setColor(textColor);
		textComponent.render(graphics);
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
		if (pastProgress == null) return;

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
			textComponent.setColor(Color.WHITE);
		}
		textComponent.render(graphics);
	}

	private float getXpProgress(int startXp, int currentXp, int goalXp)
	{
		if (goalXp - startXp == 0)
		{
			if (currentXp - startXp == 0)
			{
				return 1f;
			}
			else
			{
				return 2f;
			}
		}

		if (startXp < 0) return 0f;
		else if (goalXp < startXp) return 0f;
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
