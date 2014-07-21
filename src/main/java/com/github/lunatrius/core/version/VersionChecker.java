package com.github.lunatrius.core.version;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.lib.Reference;
import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import net.minecraft.nbt.NBTTagCompound;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VersionChecker {
	public static final String RECOMMENDED_FORGE = "\n---\nRecommended Forge: %s";
	public static final String URL = "http://mc.lunatri.us/json?latest=1&mc=%s";

	public static final String VERSION = "%s -> %s";
	public static final String UPDATEAVAILABLE = "\nUpdate is available (%s -> %s)!";
	public static final String UPTODATE = "\nUp to date!";

	public static final String UPDATEAVAILABLECON = "Update is available for %s (%s -> %s)!";
	public static final String UPTODATECON = "%s is up to date!";
	public static final String FUTURECON = "Is %s from the future?";

	public static final String DYNIOUS_VERSIONCHECKER_MODID = "VersionChecker";
	public static final String UPDATE_URL = "https://mods.io/mods?author=Lunatrius";

	private static final List<ModMetadata> REGISTERED_MODS = new ArrayList<ModMetadata>();
	private static final Map<String, String> OUTDATED_MODS = new HashMap<String, String>();
	private static boolean done = false;

	@Deprecated
	public static void registerMod(ModMetadata modMetadata) {
		registerMod(modMetadata, Reference.FORGE);
	}

	public static void registerMod(ModMetadata modMetadata, String forgeVersion) {
		REGISTERED_MODS.add(modMetadata);

		if (modMetadata.description != null) {
			modMetadata.description += String.format(RECOMMENDED_FORGE, forgeVersion);
		}
	}

	public static Set<Map.Entry<String, String>> getOutdatedMods() {
		return OUTDATED_MODS.entrySet();
	}

	public static void setDone(boolean isDone) {
		done = isDone;
	}

	public static boolean isDone() {
		return done;
	}

	public static void startVersionCheck() {
		new Thread("LunatriusCore Version Check") {
			@Override
			public void run() {
				try {
					URL url = new URL(String.format(URL, Reference.MINECRAFT));
					InputStream con = url.openStream();
					String data = new String(ByteStreams.toByteArray(con));
					con.close();

					Map<String, Object> json = new Gson().fromJson(data, Map.class);

					if (json.get("version").equals(1.0)) {
						Map<String, Map<String, Map<String, Object>>> mods = (Map<String, Map<String, Map<String, Object>>>) json.get("mods");

						for (ModMetadata modMetadata : REGISTERED_MODS) {
							String modid = modMetadata.modId;
							ArtifactVersion versionLocal = new DefaultArtifactVersion(modMetadata.version);

							try {
								Map<String, Object> latest = mods.get(modid).get("latest");
								DefaultArtifactVersion versionRemote = new DefaultArtifactVersion((String) latest.get("version"));
								int diff = versionRemote.compareTo(versionLocal);

								if (diff > 0) {
									List<String> changes = (List<String>) latest.get("changes");
									if (changes.size() == 0) {
										changes.add("No changelog available.");
									}

									if (ConfigurationHandler.canNotifyOfUpdate(modid, versionRemote.getVersionString())) {
										addOutdatedMod(modMetadata, versionLocal, versionRemote, Joiner.on("\n").join(changes));
									}
									modMetadata.description += String.format(UPDATEAVAILABLE, versionLocal, versionRemote);
									Reference.logger.info(String.format(UPDATEAVAILABLECON, modid, versionLocal, versionRemote));
								} else if (diff == 0) {
									modMetadata.description += UPTODATE;
									Reference.logger.info(String.format(UPTODATECON, modid));
								} else {
									Reference.logger.info(String.format(FUTURECON, modid));
								}

								ConfigurationHandler.addUpdate(modid, versionRemote.getVersionString());
							} catch (Exception e) {
								Reference.logger.error("Something went wrong!", e);
							}
						}
					} else {
						Reference.logger.warn(String.format("Unsupported version (%s)!", json.get("version")));
					}
				} catch (Exception e) {
					Reference.logger.error("Something went wrong!", e);
				}
				done = true;
			}
		}.start();
	}

	private static void addOutdatedMod(ModMetadata metadata, ArtifactVersion versionLocal, DefaultArtifactVersion versionRemote, String changeLog) {
		if (Loader.isModLoaded(DYNIOUS_VERSIONCHECKER_MODID)) {
			NBTTagCompound data = new NBTTagCompound();

			data.setString("modDisplayName", metadata.name);
			data.setString("oldVersion", versionLocal.getVersionString());
			data.setString("newVersion", versionRemote.getVersionString());
			data.setString("updateUrl", UPDATE_URL);
			data.setBoolean("isDirectLink", false);
			data.setString("changeLog", changeLog);

			FMLInterModComms.sendRuntimeMessage(metadata.modId, DYNIOUS_VERSIONCHECKER_MODID, "addUpdate", data);
		} else {
			OUTDATED_MODS.put(metadata.name, String.format(VERSION, versionLocal, versionRemote));
		}
	}
}
