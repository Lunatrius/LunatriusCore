package com.github.lunatrius.core.client;

import com.github.lunatrius.core.version.VersionChecker;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

import java.util.Map;
import java.util.Set;

import static cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class VersionTicker {
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if (event.phase.equals(TickEvent.Phase.END)) {
			Minecraft minecraft = Minecraft.getMinecraft();
			boolean keepTicking = minecraft == null || minecraft.thePlayer == null || minecraft.theWorld == null;

			if (!keepTicking && VersionChecker.isDone()) {
				Set<Map.Entry<String, String>> outdatedMods = VersionChecker.getOutdatedMods();

				if (outdatedMods.size() > 0) {
					minecraft.thePlayer.func_146105_b(new ChatComponentTranslation("message.updatesavailable", getChatComponentModList(outdatedMods)));
				}

				FMLCommonHandler.instance().bus().unregister(this);
			}
		}
	}

	private IChatComponent getChatComponentModList(Set<Map.Entry<String, String>> mods) {
		IChatComponent chatComponentModList = new ChatComponentText("[");

		for (Map.Entry<String, String> mod : mods) {
			if (chatComponentModList.func_150253_a().size() > 0) {
				chatComponentModList.func_150258_a(", ");
			}

			IChatComponent chatComponentMod = new ChatComponentText(mod.getKey());
			ChatStyle chatStyle = chatComponentMod.func_150256_b();

			chatStyle.func_150238_a(EnumChatFormatting.GRAY);
			chatStyle.func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getChatComponentHover(mod.getKey(), mod.getValue())));

			chatComponentModList.func_150257_a(chatComponentMod);
		}

		return chatComponentModList.func_150258_a("]");
	}

	private IChatComponent getChatComponentHover(String name, String version) {
		IChatComponent chatComponentHover = new ChatComponentText("");
		IChatComponent chatComponentModName = new ChatComponentText(name);

		chatComponentModName.func_150256_b().func_150238_a(EnumChatFormatting.GREEN);
		chatComponentHover.func_150257_a(chatComponentModName);
		chatComponentHover.func_150257_a(new ChatComponentText(": " + version));

		return chatComponentHover;
	}
}
