package com.github.lunatrius.core.client.gui;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.lib.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class GuiModConfig extends GuiConfig {
	public GuiModConfig(GuiScreen guiScreen) {
		super(guiScreen, new ConfigElement(ConfigurationHandler.configuration.getCategory(ConfigurationHandler.CATEGORY_VERCHECK)).getChildElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}
