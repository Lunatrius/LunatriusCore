package com.github.lunatrius.core.util.vector;

public class Vector4i extends Vector3i {
    public int w;

    public Vector4i() {
        this(0, 0, 0, 0);
    }

    public Vector4i(Vector4i vec) {
        this(vec.x, vec.y, vec.z, vec.w);
    }

    public Vector4i(int num) {
        this(num, num, num, num);
    }

    public Vector4i(int x, int y, int z, int w) {
        super(x, y, z);
        this.w = w;
    }

    public final int getW() {
        return this.w;
    }

    public final void setW(int w) {
        this.w = w;
    }

    public Vector4i set(Vector4i vec) {
        return set(vec.x, vec.y, vec.z, vec.w);
    }

    public Vector4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public int lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public final double lengthTo(Vector4i vec) {
        return Math.sqrt(lengthSquaredTo(vec));
    }

    public int lengthSquaredTo(Vector4i vec) {
        return pow2(this.x - vec.x) + pow2(this.y - vec.y) + pow2(this.z - vec.z) + pow2(this.w - vec.w);
    }

    @Override
    public Vector4i negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
        return this;
    }

    public double dot(Vector4i vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w * vec.w;
    }

    @Override
    public Vector4i scale(double scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        this.w *= scale;
        return this;
    }

    public Vector4i add(Vector4i vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        this.w += vec.w;
        return this;
    }

    public Vector4i add(int x, int y, int z, int w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vector4i sub(Vector4i vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        this.w -= vec.w;
        return this;
    }

    public Vector4i sub(int x, int y, int z, int w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public Vector4f toVector4f() {
        return new Vector4f(this.x, this.y, this.z, this.w);
    }

    public Vector4f toVector4f(Vector4f vec) {
        return vec.set(this.x, this.y, this.z, this.w);
    }

    public Vector4d toVector4d() {
        return new Vector4d(this.x, this.y, this.z, this.w);
    }

    public Vector4d toVector4d(Vector4d vec) {
        return vec.set(this.x, this.y, this.z, this.w);
    }

    @Override
    public Vector4i clone() {
        return new Vector4i(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector4i && equals((Vector4i) obj);
    }

    public boolean equals(Vector4i vec) {
        return this.x == vec.x && this.y == vec.y && this.z == vec.z && this.w == vec.w;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s]", this.x, this.y, this.z, this.w);
    }
}
