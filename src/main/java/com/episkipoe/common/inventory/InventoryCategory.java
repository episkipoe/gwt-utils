package com.episkipoe.common.inventory;

import com.episkipoe.common.draw.ImageDrawable;

public abstract class InventoryCategory {
	abstract public String getName();
	public String getPlural() {
		return getName()+"s";
	}
	/**
	 *   Called in response to the player acquiring an item of this category
	 * @param fileName the item picked up
	 */
	public void playerPickedUp(String fileName) { 
		//Do nothing by default
	}
	
	public ImageDrawable getInventoryDrawable(String fileName) {
		return new InventoryRoom.InventoryDrawable(fileName);		
	}
	
	public final boolean equals(Object other) {
		if(other instanceof InventoryCategory) {
			InventoryCategory category = (InventoryCategory) other;
			return getName().equals(category.getName());
		}
		return false;
	}
	public final int hashCode() { return getName().hashCode(); }
	
}
