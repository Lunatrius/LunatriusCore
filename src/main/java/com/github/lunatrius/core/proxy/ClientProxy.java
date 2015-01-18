package com.github.lunatrius.core.proxy;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.version.VersionTicker;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTickers() {
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        FMLCommonHandler.instance().bus().register(new VersionTicker());
    }
}
