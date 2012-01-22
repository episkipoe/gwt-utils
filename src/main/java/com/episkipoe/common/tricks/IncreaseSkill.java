package com.episkipoe.common.tricks;

import java.util.List;

import com.episkipoe.common.Game;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.TextUtils;

public class IncreaseSkill implements Runnable {
	Drawable removeOnRun;
	String skill; 
	int value;
	List<String> message;
	public IncreaseSkill(String skill, int value, List<String> message) {
		this.skill=skill;
		this.value=value;
	}
	@Override
	public void run() {
		Game.player.increaseSkillLevel(skill, value);
		if(removeOnRun != null) Game.room.removeDrawable(removeOnRun);
		TextUtils.growl(message);
	}
	public void removeOnRun(Drawable target) {
		removeOnRun=target;	
	}

}
