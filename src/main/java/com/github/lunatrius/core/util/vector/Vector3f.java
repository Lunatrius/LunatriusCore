package com.github.lunatrius.core.util.vector;

public class Vector3f extends Vector2f {
	float z;

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(Vector3f vec) {
		this(vec.x, vec.y, vec.z);
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
		super.set(x, y);
		this.z = z;
		return this;
	}

	@Override
	public float lengthSquared() {
		return super.lengthSquared() + this.z * this.z;
	}

	@Override
	public Vector3f negate() {
		super.negate();
		this.z = -this.z;
		return this;
	}

	public double dot(Vector3i vec) {
		return super.dot(vec) + this.z * vec.z;
	}

	@Override
	public Vector3f scale(double scale) {
		super.scale(scale);
		this.z *= scale;
		return this;
	}

	public Vector3f add(Vector3f vec) {
		super.add(vec);
		this.z += vec.z;
		return this;
	}

	public Vector3f sub(Vector3f vec) {
		super.sub(vec);
		this.z -= vec.z;
		return this;
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
		return super.equals(vec) && this.z == vec.z;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s]", this.x, this.y, this.z);
	}
}
