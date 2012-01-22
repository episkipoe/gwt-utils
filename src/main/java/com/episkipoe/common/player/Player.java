package com.episkipoe.common.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.episkipoe.common.Game;
import com.episkipoe.common.draw.ImageDrawable;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Random;

public class Player extends ImageDrawable {
	public Player() { 
		skillToLevel = new HashMap<String,Integer>();
	}
	public void reset() {
		skillToLevel = new HashMap<String,Integer>();
		money=0;
	}
	@Override public void click() {/*do nothing*/}

	int money=0;
	public int getMoney() { return money; }
	public void setMoney(Integer money) {
		this.money = money;
	}
	public void addMoney(int amt) { 
		Game.getInventory().pickupNotify("Received $"+amt);
		money+=amt; 
	}
	public void spendMoney(int amt) { 
		Game.getInventory().pickupNotify("Spent $"+amt);
		money-=amt; 
		if(money<0) money = 0;
	}
	
	public boolean wearing(Collection<String> validHats) {
		String fileName = getFilename();
		for(String hat : validHats) {
			if(hat.equals(fileName)) return true;
		}
		return false;
	}

	public boolean skillCheck(String trickFilename) {
		return (getSkillLevel(trickFilename)>Random.nextInt(100));
	}
	
	public void save(Storage localStorage) {
		localStorage.setItem("player_money", String.valueOf(Game.player.getMoney()));	
	}
	public void load(Storage localStorage) {
		String playerMoney = localStorage.getItem("player_money");
		if(playerMoney != null) {
			setMoney(Integer.valueOf(playerMoney));
		}
	}
	private Map<String,Integer> skillToLevel;
	public int getSkillLevel(String filename) {
		return skillToLevel.get(filename);
	}
	public void increaseSkillLevel(String filename, int value) {
		Game.getInventory().pickupNotify("Trick skill increased");

		int newValue = skillToLevel.get(filename)+value;
		skillToLevel.put(filename, newValue);
		
	}

	
}
