package com.episkipoe.common.inventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.dialog.Dialog;
import com.episkipoe.common.dialog.DialogElement;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.rooms.Room;
import com.google.gwt.dom.client.ImageElement;

public class Inventory extends ImageDrawable {
	private Map<InventoryCategory, Set<String> > categoryToItems;
	public Inventory() { 
		categoryToItems = new HashMap<InventoryCategory, Set<String> > ();
		
		setLocation(new Point(45,40));
		setFilename("Inventory.png");
	}

	Room previousRoom;
	@Override
	public void click() throws Exception {
		Game.switchRoom(InventoryRoom.class);
	}

	public void addItem(String filename, InventoryCategory category) {
		Set<String> itemList = categoryToItems.get(category);
		if(itemList==null) {
			itemList = new HashSet<String>();
			categoryToItems.put(category, itemList);
		}
		itemList.add(filename);
	}
	
	public Collection<String> getItemsOfCategory(InventoryCategory category) {
		return categoryToItems.get(category);
	}
	
	public void pickup(String fileName, InventoryCategory category) {
		addItem(fileName, category);
		String msg = "New " + category.getName();
		pickupNotify(msg);
		category.playerPickedUp(fileName);
		Game.save();		
	}
	
	public void pickupNotify(String msg) {
		ImageElement img = getImageElement();
		Point location = new Point(5, img.getHeight()+5);
		for(int i=0;i<5;i++) {
			if(Game.room.pointHasDrawable(location)) {
				location.y += TextUtils.font_height;
			} else {
				break;
			}
		}
		Game.room.addDrawable(new DialogElement(msg, location, 30));		
	}
	
	public static class Pickup implements Runnable {
		private String fileName;
		private InventoryCategory category;
		private String message;
		public Pickup (String fileName, InventoryCategory category, String message) {
			this.fileName = fileName;
			this.category = category;	
			this.message = message;
		}
		public Pickup (String fileName, InventoryCategory category) {
			this.fileName = fileName;
			this.category = category;	
		}
		public Pickup (ImageDrawable object, InventoryCategory category) {
			this.fileName = object.getFilename();
			this.category = category;
		}	
		public void setMessage(String message) {
			this.message = message;
		}
		public void run() {
			Game.getInventory().pickup(fileName, category);
			if(message != null) {
				Game.room.addDrawable(new Dialog(new DialogElement(Arrays.asList(message), Game.getCenterPoint())));
			}
		}	
	}

	public void fromString(String inventory) {
		if(inventory==null) return;
		String[] stringList = inventory.split("_");
		if(stringList.length < 2) return;
		for(int i = 0 ; i < stringList.length ; i+=2) {
			String fileName = stringList[i+1];
			InventoryCategory category = InventoryCategoryFactory.getCategoryByName(stringList[i]);
			addItem(fileName, category);
		}
	}
	
	public String toString() {
		if(categoryToItems == null) return null;
		String inventoryString="";
		for(InventoryCategory category: InventoryCategoryFactory.getCategories()) {
			Collection<String> itemList = getItemsOfCategory(category);
			if(itemList == null) continue;
			for(String item: itemList) {
				inventoryString += category.getName()+"_"+item+"_";
			}
		}
		return inventoryString;
	}

	public Collection<String> getAllItems() {
		Set<String> items = new HashSet<String>();
		for (Entry<InventoryCategory, Set<String>> entry : categoryToItems.entrySet()) {
			if(entry.getValue() == null || entry.getValue().isEmpty()) continue;		
			items.addAll(entry.getValue());
		}
		return items;
	}
	public boolean contains(String item) {
		for (Entry<InventoryCategory, Set<String>> entry : categoryToItems.entrySet()) {
			if(entry.getValue() == null || entry.getValue().isEmpty()) continue;
			if(entry.getValue().contains(item)) return true;
		}
		return false;
	}
	public boolean contains(InventoryCategory category, String item) {
		if(!categoryToItems.containsKey(category)) return false;
		return categoryToItems.get(category).contains(item);
	}	
	public boolean containsAll(Collection<String> items) {
		for(String i: items) {
			if(!contains(i)) return false;
		}
		return true;
	}
	
	public boolean containsAny(InventoryCategory category, Collection<String> items) {
		for(String i: items) {
			if(contains(category, i)) return true;
		}
		return false;
	}
	public boolean containsAny(Collection<String> items) {
		for(String i: items) {
			if(!contains(i)) return true;
		}
		return false;
	}	
	
}
