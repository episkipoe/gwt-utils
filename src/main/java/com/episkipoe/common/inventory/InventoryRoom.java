package com.episkipoe.common.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.episkipoe.common.Door;
import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.TextClickable;
import com.episkipoe.common.rooms.Room;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public class InventoryRoom extends Room {
	private Door exit;
	public InventoryRoom() { 
		exit = new Door();
		exit.setLocation(new Point(750, 550));
		addExit(exit);
		setBackground("InventoryBG.png");
	}

	static public class InventoryDrawable extends ImageDrawable {
		public InventoryDrawable(String fileName) { 
			setFilename(fileName);
		}
		@Override public void click() { }
	}

	@Override
	public void postDraw(Context2d context) {
		exit.draw(context);
	}

	InventoryCategory category;
	Map<Integer,Collection<String>> pageToItems=new HashMap<Integer,Collection<String>>();
	int page=0;
	
	public void setCategory(InventoryCategory category) {
		this.category = category;
		page=0;
		Collection<String> list = Game.getInventory().getItemsOfCategory(category);
		int x=0;
		int y=0;
		int maxHeight=0; 
		
		pageToItems.clear();
		int pageCounter=0;
		for(String f : list) {	
			ImageDrawable d = category.getInventoryDrawable(f);
			ImageElement img = d.getImageElement();
			if(img==null) continue;
			if(y==0) {
				y+=5+img.getHeight()*0.75;
			}
			if(x+img.getWidth() > Game.canvasWidth) {
				x = 0;
				y+=maxHeight+5;
				maxHeight=0;
			}
			if(y+img.getHeight() > (Game.canvasHeight-20)) {
				pageCounter++;
				y=0;
			}
			x+=img.getWidth()*0.5;
			x+=img.getWidth()*0.5+5;
			if(img.getHeight() > maxHeight) maxHeight = img.getHeight();	
			
			if(!pageToItems.containsKey(pageCounter)) {
				pageToItems.put(pageCounter, new ArrayList<String>());
			}	
			pageToItems.get(pageCounter).add(f);
		}
	}
	public void loadPages() {
		if(pageToItems.size() <= 1) return ;
		int x = 10;
		for(int i = 0 ; i < pageToItems.size(); i++) {
			Point location = new Point(x, Game.canvasHeight-20);
			List<String> msg = Arrays.asList("Page " + (i+1));
			TextClickable btn = new TextClickable(msg, location, new SwitchPage(i));
			btn.stopProcessing();
			if(i==page) {
				btn.setBackgroundStyle("rgba(255,255,255,0.8)");
			} else {
				btn.setBackgroundStyle("rgba(128,128,128,0.8)");
			}
			addDrawable(btn);
			x+= (TextUtils.getTextWidth(msg)+10);			
		}
	}
	public void loadItems() {
		Collection<String> list = pageToItems.get(page);
		if(list==null || list.isEmpty()) return;
		
		int x=0;
		int y=0;
		int maxHeight=0; 

		for(String f : list) {
			ImageDrawable d = category.getInventoryDrawable(f);
			ImageElement img = d.getImageElement();
			if(img==null) continue;
			if(y==0) {
				y+=5+img.getHeight()*0.75;
			}
			if(x+img.getWidth() > Game.canvasWidth) {
				x = 0;
				y+=maxHeight+5;
				maxHeight=0;
			}
			if(y+img.getHeight() > (Game.canvasHeight-20)) {
				return;
			}
			x+=img.getWidth()*0.5;
			d.setLocation(new Point(x, y));
			x+=img.getWidth()*0.5+5;
			if(img.getHeight() > maxHeight) maxHeight = img.getHeight();
			addDrawable(d);
		}
	}

	private class SwitchCategory implements Runnable {
		InventoryRoom room;
		InventoryCategory category;
		private SwitchCategory(InventoryRoom room, InventoryCategory category) {
			this.room = room;
			this.category = category;
		}
		@Override
		public void run() {
			room.setCategory(category);
			reload();
		} 
	}
	
	private class SwitchPage implements Runnable {
		int destination;
		private SwitchPage(int destination) {
			this.destination = destination;
		}
		@Override
		public void run() {		
			page=destination;
			reload();
		}
	}
	
	private void loadCategoryList() {
		int x = 10;
		for(InventoryCategory cat : InventoryCategoryFactory.getCategories()) {
			Point location = new Point(x, 20);
			List<String> msg = Arrays.asList(cat.getPlural());
			TextClickable btn = new TextClickable(msg, location, new SwitchCategory(this, cat));
			btn.stopProcessing();
			if(cat.equals(this.category)) {
				btn.setBackgroundStyle("rgba(255,255,255,0.8)");
			} else {
				btn.setBackgroundStyle("rgba(128,128,128,0.8)");
			}
			addDrawable(btn);
			x+= (TextUtils.getTextWidth(msg)+10);
		}
	}

	@Override
	public Collection<String> getRequiredImages() { return Game.getInventory().getAllItems(); }
	
	@Override
	public void onEnter() {
		reload();
	}
	
	public void reload() {
		clearDrawables();
		loadCategoryList();
		loadPages();
		loadItems();	
	}

	public boolean showHud() { return false; }
}
