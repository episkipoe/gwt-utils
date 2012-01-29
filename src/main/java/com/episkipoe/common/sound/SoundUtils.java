package com.episkipoe.common.sound;

import com.episkipoe.common.Game;

public class SoundUtils {
	public static void play(String fileName) {
		Game.sounds.stopAll();
		Game.sounds.play(fileName);
	}
	public static void playConcurrent(String fileName) {
		Game.sounds.play(fileName);
	}	
}
