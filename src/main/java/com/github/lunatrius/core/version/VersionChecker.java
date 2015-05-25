package com.github.lunatrius.core.version;

import com.github.lunatrius.core.handler.ConfigurationHandler;
import com.github.lunatrius.core.reference.Names;
import com.github.lunatrius.core.reference.Reference;
import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VersionChecker {
    public static final String VER_CHECK_API_URL = "http://mc.lunatri.us/json?latest=1&mc=%s&v=%d";
    public static final int VER_CHECK_API_VER = 2;

    public static final String UPDATE_URL = "https://mods.io/mods?author=Lunatrius";

    private static final List<ModMetadata> REGISTERED_MODS = new ArrayList<ModMetadata>();
    private static final Map<String, String> OUTDATED_MODS = new HashMap<String, String>();
    private static boolean done = false;

    public static void registerMod(ModMetadata modMetadata, String forgeVersion) {
        REGISTERED_MODS.add(modMetadata);

        if (modMetadata.description != null) {
            modMetadata.description += "\n---\nCompiled against Forge " + forgeVersion;
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
                    if (Reference.MINECRAFT == null || "null".equals(Reference.MINECRAFT)) {
                        Reference.logger.error("Minecraft version is null! This is a bug!");
                        return;
                    }

                    URL url = new URL(String.format(VER_CHECK_API_URL, Reference.MINECRAFT, VER_CHECK_API_VER));
                    InputStream con = url.openStream();
                    String data = new String(ByteStreams.toByteArray(con));
                    con.close();

                    Map<String, Object> json = new Gson().fromJson(data, Map.class);

                    if ((Double) json.get("version") == VER_CHECK_API_VER) {
                        Map<String, Map<String, Map<String, Object>>> mods = (Map<String, Map<String, Map<String, Object>>>) json.get("mods");

                        for (ModMetadata modMetadata : REGISTERED_MODS) {
                            String modid = modMetadata.modId;
                            ArtifactVersion versionLocal = new DefaultArtifactVersion(modMetadata.version);

                            try {
                                Map<String, Map<String, Object>> mod = mods.get(modid);
                                if (mod == null) {
                                    continue;
                                }

                                Map<String, Object> latest = mod.get("latest");
                                if (latest == null) {
                                    continue;
                                }

                                DefaultArtifactVersion versionRemote = new DefaultArtifactVersion((String) latest.get("version"));
                                int diff = versionRemote.compareTo(versionLocal);

                                if (diff > 0) {
                                    List<String> changes = (List<String>) latest.get("changes");
                                    if (changes == null) {
                                        changes = new ArrayList<String>();
                                    }

                                    if (changes.size() == 0) {
                                        changes.add("No changelog available.");
                                    }

                                    if (ConfigurationHandler.canNotifyOfUpdate(modid, versionRemote.getVersionString())) {
                                        addOutdatedMod(modMetadata, versionLocal, versionRemote, Joiner.on("\n").join(changes));
                                    }

                                    Reference.logger.info("Update is available for {} ({} -> {})!", modid, versionLocal, versionRemote);
                                } else if (diff == 0) {
                                    Reference.logger.info("{} is up to date!", modid);
                                } else {
                                    Reference.logger.info("Is {} from the future?", modid);
                                }

                                ConfigurationHandler.addUpdate(modid, versionRemote.getVersionString());
                            } catch (Exception e) {
                                Reference.logger.error("Something went wrong!", e);
                            }
                        }
                    } else {
                        Reference.logger.warn("Unsupported version ({})!", json.get("version"));
                    }
                } catch (Exception e) {
                    Reference.logger.error("Something went wrong!", e);
                }
                done = true;
            }
        }.start();
    }

    private static void addOutdatedMod(ModMetadata metadata, ArtifactVersion versionLocal, DefaultArtifactVersion versionRemote, String changeLog) {
        if (Loader.isModLoaded(Names.ModId.DYNIOUS_VERSION_CHECKER)) {
            NBTTagCompound data = new NBTTagCompound();

            data.setString("modDisplayName", metadata.name);
            data.setString("oldVersion", versionLocal.getVersionString());
            data.setString("newVersion", versionRemote.getVersionString());
            data.setString("updateUrl", UPDATE_URL);
            data.setBoolean("isDirectLink", false);
            data.setString("changeLog", changeLog);

            FMLInterModComms.sendRuntimeMessage(metadata.modId, Names.ModId.DYNIOUS_VERSION_CHECKER, "addUpdate", data);
        } else {
            OUTDATED_MODS.put(metadata.name, String.format("%s -> %s", versionLocal, versionRemote));
        }
    }
}
