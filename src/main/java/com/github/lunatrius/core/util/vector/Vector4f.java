package com.github.lunatrius.core.util.vector;

public class Vector4f extends Vector3f {
	float w;

	public Vector4f() {
		this(0, 0, 0, 0);
	}

	public Vector4f(Vector4f vec) {
		this(vec.x, vec.y, vec.z, vec.w);
	}

	public Vector4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}

	public final float getW() {
		return this.w;
	}

	public final void setW(float w) {
		this.w = w;
	}

	public Vector4f set(Vector4f vec) {
		return set(vec.x, vec.y, vec.z, vec.w);
	}

	public Vector4f set(float x, float y, float z, float w) {
		super.set(x, y, z);
		this.w = w;
		return this;
	}

	@Override
	public float lengthSquared() {
		return super.lengthSquared() + this.w * this.w;
	}

	@Override
	public Vector4f negate() {
		super.negate();
		this.w = -this.w;
		return this;
	}

	public double dot(Vector4i vec) {
		return super.dot(vec) + this.w * vec.w;
	}

	@Override
	public Vector4f scale(double scale) {
		super.scale(scale);
		this.w *= scale;
		return this;
	}

	public Vector4f add(Vector4f vec) {
		super.add(vec);
		this.w += vec.w;
		return this;
	}

	public Vector4f sub(Vector4f vec) {
		super.sub(vec);
		this.w -= vec.w;
		return this;
	}

	@Override
	public Vector4f clone() {
		return new Vector4f(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vector4f && equals((Vector4f) obj);
	}

	public boolean equals(Vector4f vec) {
		return super.equals(vec) && this.w == vec.w;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s, %s]", this.x, this.y, this.z, this.w);
	}
}
