package com.github.lunatrius.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DelayedGuiDisplayTicker {
    private final GuiScreen guiScreen;
    private int ticks;

    private DelayedGuiDisplayTicker(GuiScreen guiScreen, int delay) {
        this.guiScreen = guiScreen;
        this.ticks = delay;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.ticks--;

        if (this.ticks < 0) {
            Minecraft.getMinecraft().displayGuiScreen(this.guiScreen);
            FMLCommonHandler.instance().bus().unregister(this);
        }
    }

    public static void create(GuiScreen guiScreen, int delay) {
        FMLCommonHandler.instance().bus().register(new DelayedGuiDisplayTicker(guiScreen, delay));
    }
}
