package com.github.lunatrius.core.client;

import com.github.lunatrius.core.version.VersionChecker;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;

import java.util.Set;

import static cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class VersionTicker {
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END)) {
			Minecraft minecraft = Minecraft.getMinecraft();
			boolean keepTicking = minecraft == null || minecraft.thePlayer == null || minecraft.theWorld == null;

			if (!keepTicking && VersionChecker.isDone()) {
				Set<String> outdatedMods = VersionChecker.getOutdatedMods();
				if (outdatedMods.size() > 0) {
					minecraft.thePlayer.func_146105_b(new ChatComponentTranslation("message.updatesavailable", outdatedMods));
				}

				FMLCommonHandler.instance().bus().unregister(this);
			}
		}
	}
}
