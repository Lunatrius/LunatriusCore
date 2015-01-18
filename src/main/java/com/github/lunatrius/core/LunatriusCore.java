package com.github.lunatrius.core;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.proxy.CommonProxy;
import com.github.lunatrius.core.reference.Reference;
import com.github.lunatrius.core.version.VersionChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class LunatriusCore {
    @SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
    public static CommonProxy proxy;

    @NetworkCheckHandler
    public boolean checkModList(Map<String, String> versions, Side side) {
        return true;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        VersionChecker.registerMod(event.getModMetadata(), Reference.FORGE);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (ConfigurationHandler.checkForUpdates) {
            VersionChecker.startVersionCheck();
        }

        proxy.registerTickers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
