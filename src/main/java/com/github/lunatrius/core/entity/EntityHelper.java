package com.github.lunatrius.core.entity;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.core.util.vector.Vector3i;
import net.minecraft.entity.Entity;

public class EntityHelper {
	public static Vector3f getVector3fFromEntity(Entity entity) {
		return new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
	}

	public static Vector3i getVector3iFromEntity(Entity entity) {
		return new Vector3i((int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ));
	}
}
