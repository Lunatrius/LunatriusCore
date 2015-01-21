package com.github.lunatrius.core.handler;

import com.github.lunatrius.core.reference.Names;
import com.github.lunatrius.core.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigurationHandler {
    public static Configuration configuration;

    public static final boolean CHECK_FOR_UPDATES_DEFAULT = true;
    public static final boolean SILENCE_KNOWN_UPDATES_DEFAULT = false;
    public static final boolean REPLACE_IN_GAME_CONFIG_DEFAULT = true;

    public static boolean checkForUpdates = CHECK_FOR_UPDATES_DEFAULT;
    public static boolean silenceKnownUpdates = SILENCE_KNOWN_UPDATES_DEFAULT;
    public static boolean replaceInGameConfig = REPLACE_IN_GAME_CONFIG_DEFAULT;

    private static Property propCheckForUpdates = null;
    private static Property propSilenceKnownUpdates = null;
    private static Property propKnownUpdates = null;
    private static Property propReplaceInGameConfig = null;

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        propCheckForUpdates = configuration.get(Names.Config.Category.VERSION_CHECK, Names.Config.CHECK_FOR_UPDATES, CHECK_FOR_UPDATES_DEFAULT, Names.Config.CHECK_FOR_UPDATES_DESC);
        propCheckForUpdates.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.CHECK_FOR_UPDATES);
        propCheckForUpdates.setRequiresMcRestart(true);
        checkForUpdates = propCheckForUpdates.getBoolean(CHECK_FOR_UPDATES_DEFAULT);

        propSilenceKnownUpdates = configuration.get(Names.Config.Category.VERSION_CHECK, Names.Config.SILENCE_KNOWN_UPDATES, SILENCE_KNOWN_UPDATES_DEFAULT, Names.Config.SILENCE_KNOWN_UPDATES_DESC);
        propSilenceKnownUpdates.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.SILENCE_KNOWN_UPDATES);
        propSilenceKnownUpdates.setRequiresMcRestart(true);
        silenceKnownUpdates = propSilenceKnownUpdates.getBoolean(SILENCE_KNOWN_UPDATES_DEFAULT);

        propKnownUpdates = configuration.get(Names.Config.Category.VERSION_CHECK, Names.Config.KNOWN_VERSIONS, new String[0], Names.Config.KNOWN_VERSIONS_DESC);
        propKnownUpdates.setShowInGui(false);

        propReplaceInGameConfig = configuration.get(Names.Config.Category.TWEAKS, Names.Config.REPLACE_IN_GAME_CONFIG, REPLACE_IN_GAME_CONFIG_DEFAULT, Names.Config.REPLACE_IN_GAME_CONFIG_DESC);
        propReplaceInGameConfig.setLanguageKey(Names.Config.LANG_PREFIX + "." + Names.Config.REPLACE_IN_GAME_CONFIG);
        replaceInGameConfig = propReplaceInGameConfig.getBoolean(REPLACE_IN_GAME_CONFIG_DEFAULT);

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
