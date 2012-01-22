package com.episkipoe.common.inventory;

public class InventoryItem {
	public InventoryItem(String fileName, InventoryCategory category, int cost, String skillConveyed) { 
		this.fileName = fileName;
		this.category = category;
		this.cost = cost;
		this.skillConveyed = skillConveyed;
	}	
	public InventoryItem(String fileName, InventoryCategory category, int cost) { 
		this.fileName = fileName;
		this.category = category;
		this.cost = cost;
	}
	public InventoryItem(String fileName, InventoryCategory category) { 
		this.fileName = fileName;
		this.category = category;		
	}
	public String fileName;
	public InventoryCategory category;
	public String skillConveyed;
	public int cost=0;
}
