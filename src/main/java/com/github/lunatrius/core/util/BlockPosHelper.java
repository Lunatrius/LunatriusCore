package com.github.lunatrius.core.util;

import com.google.common.collect.AbstractIterator;
import net.minecraft.util.BlockPos;

import java.util.Iterator;

public class BlockPosHelper {
    public static Iterable<MBlockPos> getAllInBox(final BlockPos from, final BlockPos to) {
        final BlockPos posMin = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos posMax = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<MBlockPos>() {
            @Override
            public Iterator<MBlockPos> iterator() {
                return new AbstractIterator<MBlockPos>() {
                    private MBlockPos pos = null;
                    private int x;
                    private int y;
                    private int z;

                    @Override
                    protected MBlockPos computeNext() {
                        if (this.pos == null) {
                            this.x = posMin.getX();
                            this.y = posMin.getY();
                            this.z = posMin.getZ();
                            this.pos = new MBlockPos(this.x, this.y, this.z);
                            return this.pos;
                        }

                        if (this.pos.equals(posMax)) {
                            return this.endOfData();
                        }

                        if (this.x < posMax.getX()) {
                            this.x++;
                        } else if (this.y < posMax.getY()) {
                            this.x = posMin.getX();
                            this.y++;
                        } else if (this.z < posMax.getZ()) {
                            this.x = posMin.getX();
                            this.y = posMin.getY();
                            this.z++;
                        }

                        this.pos.x = this.x;
                        this.pos.y = this.y;
                        this.pos.z = this.z;
                        return this.pos;
                    }
                };
            }
        };
    }

    public static Iterable<MBlockPos> getAllInBoxXZY(final BlockPos from, final BlockPos to) {
        final BlockPos posMin = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos posMax = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<MBlockPos>() {
            @Override
            public Iterator<MBlockPos> iterator() {
                return new AbstractIterator<MBlockPos>() {
                    private MBlockPos pos = null;
                    private int x;
                    private int y;
                    private int z;

                    @Override
                    protected MBlockPos computeNext() {
                        if (this.pos == null) {
                            this.x = posMin.getX();
                            this.y = posMin.getY();
                            this.z = posMin.getZ();
                            this.pos = new MBlockPos(this.x, this.y, this.z);
                            return this.pos;
                        }

                        if (this.pos.equals(posMax)) {
                            return this.endOfData();
                        }

                        if (this.x < posMax.getX()) {
                            this.x++;
                        } else if (this.z < posMax.getZ()) {
                            this.x = posMin.getX();
                            this.z++;
                        } else if (this.y < posMax.getY()) {
                            this.x = posMin.getX();
                            this.z = posMin.getZ();
                            this.y++;
                        }

                        this.pos.x = this.x;
                        this.pos.y = this.y;
                        this.pos.z = this.z;
                        return this.pos;
                    }
                };
            }
        };
    }

    public static Iterable<MBlockPos> getAllInBoxYXZ(final BlockPos from, final BlockPos to) {
        final BlockPos posMin = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos posMax = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<MBlockPos>() {
            @Override
            public Iterator<MBlockPos> iterator() {
                return new AbstractIterator<MBlockPos>() {
                    private MBlockPos pos = null;
                    private int x;
                    private int y;
                    private int z;

                    @Override
                    protected MBlockPos computeNext() {
                        if (this.pos == null) {
                            this.x = posMin.getX();
                            this.y = posMin.getY();
                            this.z = posMin.getZ();
                            this.pos = new MBlockPos(this.x, this.y, this.z);
                            return this.pos;
                        }

                        if (this.pos.equals(posMax)) {
                            return this.endOfData();
                        }

                        if (this.y < posMax.getY()) {
                            this.y++;
                        } else if (this.x < posMax.getX()) {
                            this.y = posMin.getY();
                            this.x++;
                        } else if (this.z < posMax.getZ()) {
                            this.y = posMin.getY();
                            this.x = posMin.getX();
                            this.z++;
                        }

                        this.pos.x = this.x;
                        this.pos.y = this.y;
                        this.pos.z = this.z;
                        return this.pos;
                    }
                };
            }
        };
    }
}
