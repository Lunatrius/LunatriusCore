package com.github.lunatrius.core.util.vector;

public class Vector3f extends Vector2f {
	public float z;

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(Vector3f vec) {
		this(vec.x, vec.y, vec.z);
	}

	public Vector3f(float num) {
		this(num, num, num);
	}

	public Vector3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

	public final float getZ() {
		return this.z;
	}

	public final void setZ(float z) {
		this.z = z;
	}

	public Vector3f set(Vector3f vec) {
		return set(vec.x, vec.y, vec.z);
	}

	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	public final double lengthTo(Vector3f vec) {
		return Math.sqrt(lengthSquaredTo(vec));
	}

	public float lengthSquaredTo(Vector3f vec) {
		return pow2(this.x - vec.x) + pow2(this.y - vec.y) + pow2(this.z - vec.z);
	}

	@Override
	public Vector3f negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}

	public double dot(Vector3i vec) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}

	@Override
	public Vector3f scale(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	public Vector3f add(Vector3f vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}

	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3f sub(Vector3f vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}

	public Vector3f sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vector3i toVector3i() {
		return new Vector3i((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
	}

	public Vector3i toVector3i(Vector3i vec) {
		return vec.set((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
	}

	@Override
	public Vector3f clone() {
		return new Vector3f(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vector3f && equals((Vector3f) obj);
	}

	public boolean equals(Vector3f vec) {
		return equals(vec, FLOAT_EPSILON);
	}

	public boolean equals(Vector3f vec, float epsilon) {
		return Math.abs(this.x - vec.x) < epsilon && Math.abs(this.y - vec.y) < epsilon && Math.abs(this.z - vec.z) < epsilon;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s]", this.x, this.y, this.z);
	}
}
