package com.github.lunatrius.core.handler;

import com.github.lunatrius.core.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.GuiIngameModOptions;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class GuiHandler {
    @SubscribeEvent
    public void onGuiOpen(final GuiOpenEvent event) {
        if (ConfigurationHandler.replaceInGameConfig && event.gui instanceof GuiIngameModOptions) {
            event.gui = new GuiModConfigList(Minecraft.getMinecraft().currentScreen);
        }
    }

    private static class GuiModConfigList extends GuiModList {
        public GuiModConfigList(final GuiScreen screen) {
            super(screen);

            try {
                final Field fieldMods = ReflectionHelper.findField(GuiModList.class, "mods");
                final List<ModContainer> mods = (List<ModContainer>) fieldMods.get(this);
                final Iterator<ModContainer> iterator = mods.iterator();
                while (iterator.hasNext()) {
                    final ModContainer mod = iterator.next();
                    final IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(mod);
                    if (guiFactory == null || guiFactory.mainConfigGuiClass() == null) {
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                Reference.logger.error("Failed to tweak mod list!", e);
            }
        }
    }
}
