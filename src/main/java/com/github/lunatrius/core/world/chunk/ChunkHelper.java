package com.github.lunatrius.core.world.chunk;

import java.util.Random;

public class ChunkHelper {
    private static final Random RANDOM = new Random();

    public static boolean isSlimeChunk(long seed, int x, int z) {
        RANDOM.setSeed(seed + (x * x * 4987142) + (x * 5947611) + (z * z * 4392871) + (z * 389711) ^ 987234911);
        return RANDOM.nextInt(10) == 0;
    }
}
