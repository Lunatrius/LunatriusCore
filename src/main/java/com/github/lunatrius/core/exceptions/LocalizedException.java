package com.github.lunatrius.core.exceptions;

import net.minecraft.util.StatCollector;

public class LocalizedException extends Exception {
    public LocalizedException(final String format, final Object... arguments) {
        super(StatCollector.translateToLocalFormatted(format, arguments));
    }
}
