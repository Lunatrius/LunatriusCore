package com.github.lunatrius.core.reference;

@SuppressWarnings("HardCodedStringLiteral")
public final class Names {
	public static final class Config {
		public static final class Category {
			public static final String VERSIONCHECK = "versioncheck";
		}

		public static final String CHECK_FOR_UPDATES = "checkForUpdates";
		public static final String CHECK_FOR_UPDATES_DESC = "Should the mod check for updates?";
		public static final String SILENCE_KNOWN_UPDATES = "silenceKnownUpdates";
		public static final String SILENCE_KNOWN_UPDATES_DESC = "Should the mod remind you only for new updates (once per version)?";
		public static final String KNOWN_VERSIONS = "knownVersions";
		public static final String KNOWN_VERSIONS_DESC = "A list of known updates. Deleting versions from the list will remind you about them again.";

		public static final String LANG_PREFIX = Reference.MODID.toLowerCase() + ".config";
	}
}
