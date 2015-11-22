package com.github.lunatrius.core.proxy;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.version.VersionTicker;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        FMLCommonHandler.instance().bus().register(new VersionTicker());
    }
}
