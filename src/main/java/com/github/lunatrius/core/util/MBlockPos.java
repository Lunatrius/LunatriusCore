package com.github.lunatrius.core.util;

import com.google.common.collect.AbstractIterator;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

import java.util.Iterator;

public class MBlockPos extends BlockPos {
    public int x;
    public int y;
    public int z;

    public MBlockPos() {
        this(0, 0, 0);
    }

    public MBlockPos(Entity source) {
        this(source.posX, source.posY, source.posZ);
    }

    public MBlockPos(Vec3 source) {
        this(source.xCoord, source.yCoord, source.zCoord);
    }

    public MBlockPos(Vec3i source) {
        this(source.getX(), source.getY(), source.getZ());
    }

    public MBlockPos(double x, double y, double z) {
        this(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public MBlockPos(int x, int y, int z) {
        super(0, 0, 0);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MBlockPos set(Entity source) {
        return set(source.posX, source.posY, source.posZ);
    }

    public MBlockPos set(Vec3 source) {
        return set(source.xCoord, source.yCoord, source.zCoord);
    }

    public MBlockPos set(Vec3i source) {
        return set(source.getX(), source.getY(), source.getZ());
    }

    public MBlockPos set(double x, double y, double z) {
        return set(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public MBlockPos set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public MBlockPos add(Vec3i vec) {
        return add(vec.getX(), vec.getY(), vec.getZ());
    }

    @Override
    public MBlockPos add(double x, double y, double z) {
        return add(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    @Override
    public MBlockPos add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public MBlockPos multiply(int factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    @SuppressWarnings("override")
    public MBlockPos subtract(Vec3i vec) {
        return subtract(vec.getX(), vec.getY(), vec.getZ());
    }

    public MBlockPos subtract(double x, double y, double z) {
        return subtract(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public MBlockPos subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public MBlockPos up() {
        return up(1);
    }

    @Override
    public MBlockPos up(int n) {
        return offset(EnumFacing.UP, n);
    }

    @Override
    public MBlockPos down() {
        return down(1);
    }

    @Override
    public MBlockPos down(int n) {
        return offset(EnumFacing.DOWN, n);
    }

    @Override
    public MBlockPos north() {
        return north(1);
    }

    @Override
    public MBlockPos north(int n) {
        return offset(EnumFacing.NORTH, n);
    }

    @Override
    public MBlockPos south() {
        return south(1);
    }

    @Override
    public MBlockPos south(int n) {
        return offset(EnumFacing.SOUTH, n);
    }

    @Override
    public MBlockPos west() {
        return west(1);
    }

    @Override
    public MBlockPos west(int n) {
        return offset(EnumFacing.WEST, n);
    }

    @Override
    public MBlockPos east() {
        return east(1);
    }

    @Override
    public MBlockPos east(int n) {
        return offset(EnumFacing.EAST, n);
    }

    @Override
    public MBlockPos offset(EnumFacing facing) {
        return offset(facing, 1);
    }

    @Override
    public MBlockPos offset(EnumFacing facing, int n) {
        this.x += facing.getFrontOffsetX() * n;
        this.y += facing.getFrontOffsetY() * n;
        this.z += facing.getFrontOffsetZ() * n;
        return this;
    }

    @Override
    public MBlockPos crossProductBP(Vec3i vec) {
        return new MBlockPos(this.y * vec.getZ() - this.z * vec.getY(), this.z * vec.getX() - this.x * vec.getZ(), this.x * vec.getY() - this.y * vec.getX());
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    public static Iterable<MBlockPos> getAllInBox(BlockPos from, BlockPos to) {
        final BlockPos start = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos end = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<MBlockPos>() {
            @Override
            public Iterator<MBlockPos> iterator() {
                return new AbstractIterator<MBlockPos>() {
                    private MBlockPos pos = null;

                    @Override
                    protected MBlockPos computeNext() {
                        if (this.pos == null) {
                            this.pos = new MBlockPos(start.getX(), start.getY(), start.getZ());
                            return this.pos;
                        } else if (this.pos.equals(end)) {
                            return endOfData();
                        } else {
                            int x = this.pos.x;
                            int y = this.pos.y;
                            int z = this.pos.z;

                            if (x < end.getX()) {
                                x++;
                            } else if (y < end.getY()) {
                                x = start.getX();
                                y++;
                            } else if (z < end.getZ()) {
                                x = start.getX();
                                y = start.getY();
                                z++;
                            }

                            this.pos.x = x;
                            this.pos.y = y;
                            this.pos.z = z;
                            return this.pos;
                        }
                    }
                };
            }
        };
    }
}
