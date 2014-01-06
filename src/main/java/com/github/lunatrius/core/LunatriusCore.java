package com.github.lunatrius.core;

import com.github.lunatrius.core.version.VersionChecker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "LunatriusCore")
public class LunatriusCore {
	@SidedProxy(serverSide = "com.github.lunatrius.core.CommonProxy", clientSide = "com.github.lunatrius.core.client.ClientProxy")
	public static CommonProxy proxy;

	public static Logger logger = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		VersionChecker.registerMod(event.getModMetadata());

		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		VersionChecker.startVersionCheck();

		proxy.registerTickers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
