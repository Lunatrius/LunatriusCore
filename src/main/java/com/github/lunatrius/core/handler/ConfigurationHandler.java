package com.github.lunatrius.core.handler;

import com.github.lunatrius.core.reference.Names;
import com.github.lunatrius.core.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigurationHandler {
    public static Configuration configuration;

    public static final boolean CHECKFORUPDATES_DEFAULT = true;
    public static final boolean SILENCEKNOWNUPDATES_DEFAULT = false;

    public static boolean checkForUpdates = CHECKFORUPDATES_DEFAULT;
    public static boolean silenceKnownUpdates = SILENCEKNOWNUPDATES_DEFAULT;

    private static Property propCheckForUpdates = null;
    private static Property propSilenceKnownUpdates = null;
    private static Property propKnownUpdates = null;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        propCheckForUpdates = configuration.get(Names.Config.Category.VERSIONCHECK, Names.Config.CHECK_FOR_UPDATES, CHECKFORUPDATES_DEFAULT, Names.Config.CHECK_FOR_UPDATES_DESC);
        propCheckForUpdates.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.CHECK_FOR_UPDATES);
        propCheckForUpdates.setRequiresMcRestart(true);
        checkForUpdates = propCheckForUpdates.getBoolean(CHECKFORUPDATES_DEFAULT);

        propSilenceKnownUpdates = configuration.get(Names.Config.Category.VERSIONCHECK, Names.Config.SILENCE_KNOWN_UPDATES, SILENCEKNOWNUPDATES_DEFAULT, Names.Config.SILENCE_KNOWN_UPDATES_DESC);
        propSilenceKnownUpdates.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.SILENCE_KNOWN_UPDATES);
        propSilenceKnownUpdates.setRequiresMcRestart(true);
        silenceKnownUpdates = propSilenceKnownUpdates.getBoolean(SILENCEKNOWNUPDATES_DEFAULT);

        propKnownUpdates = configuration.get(Names.Config.Category.VERSIONCHECK, Names.Config.KNOWN_VERSIONS, new String[0], Names.Config.KNOWN_VERSIONS_DESC);
        propKnownUpdates.setShowInGui(false);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void addUpdate(String modid, String version) {
        List<String> knownUpdatesList = new ArrayList<String>(Arrays.asList(propKnownUpdates.getStringList()));
        String update = String.format("%s %s", modid, version);
        if (!knownUpdatesList.contains(update)) {
            knownUpdatesList.add(update);
        }
        Collections.sort(knownUpdatesList);

        String[] knownUpdates = new String[knownUpdatesList.size()];
        knownUpdates = knownUpdatesList.toArray(knownUpdates);
        propKnownUpdates.set(knownUpdates);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean canNotifyOfUpdate(String modid, String version) {
        return !Arrays.asList(propKnownUpdates.getStringList()).contains(String.format("%s %s", modid, version)) || !silenceKnownUpdates;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MODID)) {
            loadConfiguration();
        }
    }
}
