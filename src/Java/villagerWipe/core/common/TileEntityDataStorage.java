package villagerWipe.core.common;

import java.util.UUID;

import net.minecraft.tileentity.TileEntity;

public class TileEntityDataStorage {

	private UUID ID;
	private String name;
	private TileEntity tile = null;
	private int xPos = 0;
	private int yPos = 0;
	private int zPos = 0;

	public TileEntityDataStorage(){}

	public TileEntityDataStorage(UUID id, String name, TileEntity tile, int xPos, int yPos, int zPos){
		this.ID = id;
		this.name = name;
		this.tile = tile;
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

	public TileEntity getTileEntity() {
		return tile;
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

	public void setMob(TileEntity tile) {
		this.tile = tile;
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
