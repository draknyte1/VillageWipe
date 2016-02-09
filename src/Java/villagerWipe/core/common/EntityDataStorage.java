package villagerWipe.core.common;

import java.util.UUID;

import net.minecraft.entity.Entity;

public class EntityDataStorage {

	private UUID ID;
	private String name;
	private Entity mob = null;
	private int xPos = 0;
	private int yPos = 0;
	private int zPos = 0;

	public EntityDataStorage(){}

	public EntityDataStorage(UUID id, String name, Entity mob, int xPos, int yPos, int zPos){
		this.ID = id;
		this.mob = mob;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}

	public UUID getId() {
		return ID;
	}
	
	public String getName() {
		return name;
	}

	public Entity getMob() {
		return mob;
	}

	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public int getZ() {
		return zPos;
	}

	public void setID(UUID uuid) {
		this.ID = uuid;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setMob(Entity mob) {
		this.mob = mob;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}
	
	public void setY(int yPos) {
		this.yPos = yPos;
	}
	
	public void setZ(int zPos) {
		this.zPos = zPos;
	}

	
}
