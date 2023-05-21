package com.ericversteeg;

import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

class XpGoalsOverlay extends Overlay {

	private final Client client;
	private final XpGoalsPlugin plugin;
	private final XpGoalsConfig config;

	private final ItemManager itemManager;

	@Inject
	private XpGoalsOverlay(Client client, XpGoalsPlugin plugin, XpGoalsConfig config, ItemManager itemManager) {
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);

		this.client = client;
		this.plugin = plugin;
		this.config = config;

		this.itemManager = itemManager;
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		return null;
	}
}
