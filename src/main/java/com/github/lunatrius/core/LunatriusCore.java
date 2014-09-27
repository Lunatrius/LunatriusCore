package com.github.lunatrius.core;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.proxy.CommonProxy;
import com.github.lunatrius.core.reference.Reference;
import com.github.lunatrius.core.version.VersionChecker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class LunatriusCore {
    @SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        VersionChecker.registerMod(event.getModMetadata(), Reference.FORGE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (ConfigurationHandler.checkForUpdates) {
            VersionChecker.startVersionCheck();
        }

        proxy.registerTickers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
