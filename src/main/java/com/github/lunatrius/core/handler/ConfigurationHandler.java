package com.github.lunatrius.core.handler;

import com.github.lunatrius.core.lib.Reference;
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
	public static final String CATEGORY_VERCHECK = "versioncheck";
	public static final String CHECKFORUPDATES = "checkForUpdates";
	public static final String CHECKFORUPDATES_DESC = "Should the mod check for updates?";
	public static final String SILENCEKNOWNUPDATES = "silenceKnownUpdates";
	public static final String SILENCEKNOWNUPDATES_DESC = "Should the mod remind you only for new updates (once per version)?";
	public static final String KNOWNVERSIONS = "knownVersions";
	public static final String KNOWNVERSIONS_DESC = "A list of known updates. Deleting versions from the list will remind you about them again.";
	public static final String LANG_PREFIX = Reference.MODID.toLowerCase();

	public static Configuration configuration;

	public static boolean checkForUpdates = true;
	public static boolean silenceKnownUpdates = false;

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
		propCheckForUpdates = configuration.get(CATEGORY_VERCHECK, CHECKFORUPDATES, checkForUpdates, CHECKFORUPDATES_DESC);
		propCheckForUpdates.setLanguageKey(String.format("%s.%s", LANG_PREFIX, CHECKFORUPDATES));
		propCheckForUpdates.setRequiresMcRestart(true);
		checkForUpdates = propCheckForUpdates.getBoolean(checkForUpdates);

		propSilenceKnownUpdates = configuration.get(CATEGORY_VERCHECK, SILENCEKNOWNUPDATES, silenceKnownUpdates, SILENCEKNOWNUPDATES_DESC);
		propSilenceKnownUpdates.setLanguageKey(String.format("%s.%s", LANG_PREFIX, SILENCEKNOWNUPDATES));
		propSilenceKnownUpdates.setRequiresMcRestart(true);
		silenceKnownUpdates = propSilenceKnownUpdates.getBoolean(silenceKnownUpdates);

		propKnownUpdates = configuration.get(CATEGORY_VERCHECK, KNOWNVERSIONS, new String[0], KNOWNVERSIONS_DESC);
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
