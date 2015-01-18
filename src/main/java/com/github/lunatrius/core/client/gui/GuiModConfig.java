package com.github.lunatrius.core.client.gui;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.reference.Names;
import com.github.lunatrius.core.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiModConfig extends GuiConfig {
    public GuiModConfig(GuiScreen guiScreen) {
        super(guiScreen, new ConfigElement(ConfigurationHandler.configuration.getCategory(Names.Config.Category.VERSIONCHECK)).getChildElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
    }
}
